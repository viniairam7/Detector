package com.projetoA3.detector.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Transacao;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.TransacaoRepositorio;
import java.time.LocalDateTime;

@Service
public class TransacaoServicoImpl implements TransacaoServico {

    private final TransacaoRepositorio transacaoRepositorio;
    private final CartaoRepositorio cartaoRepositorio;
    private final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Autowired
    public TransacaoServicoImpl(TransacaoRepositorio transacaoRepositorio, CartaoRepositorio cartaoRepositorio) {
        this.transacaoRepositorio = transacaoRepositorio;
        this.cartaoRepositorio = cartaoRepositorio;
    }

    @Override
    public Transacao registrarTransacao(TransacaoDTO transacaoDto) {
        // Passo 1: Buscar o cartão pelo ID informado no DTO
        Cartao cartao = cartaoRepositorio.findById(transacaoDto.getCartaoId())
                .orElseThrow(() -> new RuntimeException("Cartão não encontrado com o ID: " + transacaoDto.getCartaoId()));

        // Passo 2: Criar a nova entidade Transacao
        Transacao novaTransacao = new Transacao();
        novaTransacao.setValor(transacaoDto.getValor());
        novaTransacao.setEstabelecimento(transacaoDto.getEstabelecimento());
        novaTransacao.setCartao(cartao);
        novaTransacao.setDataHora(LocalDateTime.now()); // Define a data e hora atuais

        // Futuramente, aqui entrará a lógica de detecção de fraude
        // Ex: if (isFraudulenta(novaTransacao)) { ... }

// --- LÓGICA DE GEOLOCALIZAÇÃO ADICIONADA ---

        // 1. Criar o objeto Point a partir da lat/lon do DTO
        // IMPORTANTE: O padrão JTS é (X, Y) que corresponde a (Longitude, Latitude)
        Point localizacaoPonto = geometryFactory.createPoint(
            new Coordinate(transacaoDto.getLongitude(), transacaoDto.getLatitude())
        );
        
        // 2. Salvar o Ponto e o IP na entidade
        novaTransacao.setLocalizacao(localizacaoPonto);
        novaTransacao.setIpAddress(transacaoDto.getIpAddress());
        
        // --- FIM DA LÓGICA DE GEOLOCALIZAÇÃO ---

        // Futuramente, aqui entrará a lógica de detecção de fraude
        // Ex: if (isFraudulenta(novaTransacao)) { ... }

        // Passo 3: Salvar a transação no banco
        return transacaoRepositorio.save(novaTransacao);
    }
}