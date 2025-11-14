package com.projetoA3.detector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable; // <-- IMPORTAR
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.dto.TransacaoResponseDTO; // <-- IMPORTAR
import com.projetoA3.detector.entity.Transacao;
import com.projetoA3.detector.service.TransacaoServico;

@RestController
@RequestMapping("/api/transacoes")
public class TransacaoController {

    private final TransacaoServico transacaoServico;

    @Autowired
    public TransacaoController(TransacaoServico transacaoServico) {
        this.transacaoServico = transacaoServico;
    }

    @PostMapping
    public ResponseEntity<TransacaoResponseDTO> registrarTransacao(@RequestBody TransacaoDTO transacaoDto) {
        // Agora o serviço retorna o DTO de Resposta
        TransacaoResponseDTO resposta = transacaoServico.registrarTransacao(transacaoDto);
        
        // Retornamos OK (200) em ambos os casos (COMPLETED ou PENDING)
        // O frontend decidirá o que fazer com base no 'statusResposta'
        return ResponseEntity.ok(resposta);
    }

    // --- NOVOS ENDPOINTS DE CONFIRMAÇÃO ---

    /**
     * Endpoint para o usuário aceitar uma transação pendente.
     */
    @PostMapping("/{id}/confirmar")
    public ResponseEntity<Transacao> confirmarTransacao(@PathVariable Long id) {
        Transacao transacao = transacaoServico.confirmarTransacao(id);
        return ResponseEntity.ok(transacao);
    }

    /**
     * Endpoint para o usuário negar uma transação pendente.
     */
    @PostMapping("/{id}/negar")
    public ResponseEntity<Transacao> negarTransacao(@PathVariable Long id) {
        Transacao transacao = transacaoServico.negarTransacao(id);
        return ResponseEntity.ok(transacao);
    }
}