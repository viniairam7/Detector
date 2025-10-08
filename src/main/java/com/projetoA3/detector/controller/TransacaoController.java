package com.projetoA3.detector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoA3.detector.dto.TransacaoDTO;
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
    public ResponseEntity<Transacao> registrarTransacao(@RequestBody TransacaoDTO transacaoDto) {
        Transacao transacaoSalva = transacaoServico.registrarTransacao(transacaoDto);
        return new ResponseEntity<>(transacaoSalva, HttpStatus.CREATED);
    }
}