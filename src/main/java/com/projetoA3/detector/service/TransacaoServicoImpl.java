package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.dto.TransacaoResponseDTO; // <-- IMPORTAR
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Transacao;
import com.projetoA3.detector.entity.TransacaoStatus; // <-- IMPORTAR
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.TransacaoRepositorio;
// import com.projetoA3.detector.exception.FraudDetectedException; // <-- NÃO VAMOS MAIS USAR

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.LocalDateTime;
// import java.util.List; // Não é mais necessário para a lógica antiga
import java.util.Optional; // <-- IMPORTAR

@Service
public class TransacaoServicoImpl implements TransacaoServico {

    private final TransacaoRepositorio transacaoRepositorio;
    private final CartaoRepositorio cartaoRepositorio;
    private final UsuarioServico usuarioServico; 
    
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    
    // --- LIMITES DAS REGRAS DE FRAUDE ---
    private static final double FATOR_DESVIO_GASTO = 3.0; 
    private static final double DISTANCIA_MAXIMA_KM_PERMITIDA = 25.0; 

    @Autowired
    public TransacaoServicoImpl(TransacaoRepositorio transacaoRepositorio, 
                                CartaoRepositorio cartaoRepositorio,
                                UsuarioServico usuarioServico) {
        this.transacaoRepositorio = transacaoRepositorio;
        this.cartaoRepositorio = cartaoRepositorio;
        this.usuarioServico = usuarioServico;
    }

    @Override
    @Transactional
    public TransacaoResponseDTO registrarTransacao(TransacaoDTO transacaoDto) {
        
        Cartao cartao = cartaoRepositorio.findById(transacaoDto.getCartaoId())
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado com o ID: " + transacaoDto.getCartaoId()));
        Usuarios usuario = cartao.getUsuario();

        // --- LÓGICA DE DETECÇÃO (NÃO MAIS LANÇA EXCEÇÃO) ---
        String mensagemFraude = null; // Armazena a mensagem de fraude, se houver

        // REGRA TIPO 1: Gasto Atípico
        if (usuario.getMediaGasto() != null && usuario.getMediaGasto().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal mediaHabitual = usuario.getMediaGasto();
            BigDecimal limiteGasto = mediaHabitual.multiply(new BigDecimal(FATOR_DESVIO_GASTO));
            
            if (transacaoDto.getValor().compareTo(limiteGasto) > 0) {
                mensagemFraude = String.format("ALERTA: Valor (R$ %.2f) muito acima da sua média (R$ %.2f).",
                                  transacaoDto.getValor(), mediaHabitual);
            }
        }

        // REGRA TIPO 2: Horário Atípico (só checa se a regra 1 não pegou)
        if (mensagemFraude == null && usuario.getHorarioHabitualInicio() != null && usuario.getHorarioHabitualFim() != null) {
            LocalTime horaTransacao = LocalTime.now();
            if (horaTransacao.isBefore(usuario.getHorarioHabitualInicio()) || 
                horaTransacao.isAfter(usuario.getHorarioHabitualFim())) {
                
                mensagemFraude = String.format("ALERTA: Compra às %s, fora do seu horário habitual (%s - %s).",
                                  horaTransacao, usuario.getHorarioHabitualInicio(), usuario.getHorarioHabitualFim());
            }
        }

        // REGRA TIPO 3: Localização (checa se regras 1 e 2 não pegaram)
        // Esta é a regra de maior importância, vamos checá-la por último
        if (mensagemFraude == null) {
            Double distanciaKm = transacaoRepositorio.calcularDistanciaEntrePontosKm(
                transacaoDto.getLongitude(), transacaoDto.getLatitude(),
                transacaoDto.getLongitudeUsuario(), transacaoDto.getLatitudeUsuario()
            );

            if (distanciaKm != null && distanciaKm > DISTANCIA_MAXIMA_KM_PERMITIDA) {
                mensagemFraude = String.format("ALERTA: Compra a %.2f km da sua localização atual.", distanciaKm);
            }
        }
        
        // --- FIM DAS DETECÇÕES ---

        // 2. Criar e salvar a transação
        Point localizacaoPonto = geometryFactory.createPoint(
            new Coordinate(transacaoDto.getLongitude(), transacaoDto.getLatitude())
        );

        Transacao novaTransacao = new Transacao();
        novaTransacao.setValor(transacaoDto.getValor());
        novaTransacao.setEstabelecimento(transacaoDto.getEstabelecimento());
        novaTransacao.setCartao(cartao);
        novaTransacao.setDataHora(LocalDateTime.now());
        novaTransacao.setLocalizacao(localizacaoPonto);
        novaTransacao.setIpAddress(transacaoDto.getIpAddress());
        
        // --- DECISÃO DE STATUS ---
        if (mensagemFraude != null) {
            // FRAUDE DETECTADA! Salva como PENDENTE.
            novaTransacao.setStatus(TransacaoStatus.PENDING);
            Transacao transacaoSalva = transacaoRepositorio.save(novaTransacao);
            
            // Retorna a resposta especial para o frontend
            return new TransacaoResponseDTO("PENDING_CONFIRMATION", mensagemFraude, transacaoSalva);
        } else {
            // NENHUMA FRAUDE. Salva como COMPLETA.
            novaTransacao.setStatus(TransacaoStatus.COMPLETED);
            Transacao transacaoSalva = transacaoRepositorio.save(novaTransacao);
            
            // ATUALIZA OS PADRÕES (pois a transação é válida)
            usuarioServico.atualizarPadroesUsuario(usuario, transacaoSalva);
            
            // Retorna a resposta padrão
            return new TransacaoResponseDTO("COMPLETED", "Transação registrada com sucesso.", transacaoSalva);
        }
    }

    // --- IMPLEMENTAÇÃO DOS NOVOS MÉTODOS ---

    @Override
    @Transactional
    public Transacao confirmarTransacao(Long transacaoId) {
        Transacao transacao = transacaoRepositorio.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada."));

        if (transacao.getStatus() != TransacaoStatus.PENDING) {
            throw new IllegalStateException("Esta transação não está pendente de confirmação.");
        }

        // Atualiza o status
        transacao.setStatus(TransacaoStatus.COMPLETED);
        
        // **IMPORTANTE**: Agora que foi confirmada, ATUALIZAMOS O PADRÃO do usuário
        Usuarios usuario = transacao.getCartao().getUsuario();
        usuarioServico.atualizarPadroesUsuario(usuario, transacao);
        
        return transacaoRepositorio.save(transacao);
    }

    @Override
    @Transactional
    public Transacao negarTransacao(Long transacaoId) {
        Transacao transacao = transacaoRepositorio.findById(transacaoId)
                .orElseThrow(() -> new RuntimeException("Transação não encontrada."));

        if (transacao.getStatus() != TransacaoStatus.PENDING) {
            throw new IllegalStateException("Esta transação não está pendente de confirmação.");
        }

        // Apenas atualiza o status
        transacao.setStatus(TransacaoStatus.DENIED);
        
        // Não atualizamos o padrão do usuário, pois foi negada
        
        return transacaoRepositorio.save(transacao);
    }
}