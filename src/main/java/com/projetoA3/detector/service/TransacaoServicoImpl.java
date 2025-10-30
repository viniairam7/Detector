package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Transacao;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.TransacaoRepositorio;
import com.projetoA3.detector.exception.FraudDetectedException; // IMPORTA A EXCEÇÃO

// Imports necessários para a nova lógica
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Importe este

import java.time.Duration; // Importe este
import java.time.LocalDateTime;
import java.util.List; // Importe este

@Service
public class TransacaoServicoImpl implements TransacaoServico {

    private final TransacaoRepositorio transacaoRepositorio;
    private final CartaoRepositorio cartaoRepositorio;
    
    // SRID 4326 é o padrão para coordenadas GPS (WGS84)
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    
    // Velocidade máxima de viagem em km/h (ex: avião comercial)
    private static final double VELOCIDADE_MAXIMA_KMH = 900.0; 

    @Autowired
    public TransacaoServicoImpl(TransacaoRepositorio transacaoRepositorio, CartaoRepositorio cartaoRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
        this.cartaoRepositorio = cartaoRepositorio;
    }

    @Override
    @Transactional // Garante que tudo (salvar e checar) ocorra na mesma transação
    public Transacao registrarTransacao(TransacaoDTO transacaoDto) {
        
        // 1. Encontrar o cartão
        Cartao cartao = cartaoRepositorio.findById(transacaoDto.getCartaoId())
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado com o ID: " + transacaoDto.getCartaoId()));

        // 2. Criar o objeto Point a partir da lat/lon
        Point localizacaoPonto = geometryFactory.createPoint(
            new Coordinate(transacaoDto.getLongitude(), transacaoDto.getLatitude())
        );

        // 3. Criar e salvar a nova transação
        Transacao novaTransacao = new Transacao();
        novaTransacao.setValor(transacaoDto.getValor());
        novaTransacao.setEstabelecimento(transacaoDto.getEstabelecimento());
        novaTransacao.setCartao(cartao);
        novaTransacao.setDataHora(LocalDateTime.now());
        novaTransacao.setLocalizacao(localizacaoPonto);
        novaTransacao.setIpAddress(transacaoDto.getIpAddress());
        
        // Salva a transação. Se uma exceção for lançada abaixo, o @Transactional
        // vai reverter (fazer rollback) deste save.
        Transacao transacaoSalva = transacaoRepositorio.save(novaTransacao);

        // --- INÍCIO DA LÓGICA DE DETECÇÃO DE FRAUDE ---
        
        // 4. Buscar o histórico de transações deste cartão (já ordenado pela data)
        List<Transacao> historico = transacaoRepositorio.findByCartaoIdOrderByDataHoraDesc(cartao.getId());

        // 5. Verificar se existe uma transação anterior para comparar
        if (historico.size() > 1) {
            Transacao transacaoAnterior = historico.get(1); // 0 é a atual, 1 é a anterior

            // 6. Calcular a distância em KM usando a query nativa do PostGIS
            Double distanciaKm = transacaoRepositorio.calcularDistanciaEmKm(
                transacaoSalva.getId(), 
                transacaoAnterior.getId()
            );

            // 7. Calcular o tempo decorrido em horas
            long segundosDecorridos = Duration.between(transacaoAnterior.getDataHora(), transacaoSalva.getDataHora()).toSeconds();
            double horasDecorridas = segundosDecorridos / 3600.0;

            // 8. Calcular a velocidade (evitar divisão por zero se o tempo for muito curto)
            // Ignora se for menos de 1 minuto (para evitar falsos positivos)
            if (distanciaKm != null && horasDecorridas > (1.0 / 60.0)) { 
                double velocidadeKmh = distanciaKm / horasDecorridas;

                System.out.println("--- DEBUG DE VELOCIDADE ---");
                System.out.println("Distância: " + distanciaKm + " km");
                System.out.println("Tempo: " + horasDecorridas + " horas");
                System.out.println("Velocidade Calculada: " + velocidadeKmh + " km/h");
                System.out.println("---------------------------");

                if (velocidadeKmh > VELOCIDADE_MAXIMA_KMH) {
                    // *** MUDANÇA PRINCIPAL AQUI ***
                    // Em vez de imprimir no log, lançamos a exceção.
                    // Isso vai parar o método e retornar um erro 400 para o usuário.
                    throw new FraudDetectedException("ALERTA DE FRAUDE: VIAGEM IMPOSSÍVEL DETECTADA!");
                }
            }
        }
        
        // --- FIM DA LÓGICA DE DETECÇÃO DE FRAUDE ---

        // Se nenhuma exceção foi lançada, a transação é válida.
        return transacaoSalva;
    }
}