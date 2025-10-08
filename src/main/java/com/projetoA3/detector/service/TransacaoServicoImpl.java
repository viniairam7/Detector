package com.projetoA3.detector.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Transacao;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.TransacaoRepositorio;

@Service
public class TransacaoServicoImpl implements TransacaoServico {

    private final TransacaoRepositorio transacaoRepositorio;
    private final CartaoRepositorio cartaoRepositorio;

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

        // Passo 3: Salvar a transação no banco
        return transacaoRepositorio.save(novaTransacao);
    }
}