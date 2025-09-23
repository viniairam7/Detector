package com.projetoA3.detector.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.service.CartaoServico;

@RestController
@RequestMapping("/api/cartoes")
public class CartaoController {

    private final CartaoServico cartaoServico;

    @Autowired
    public CartaoController(CartaoServico cartaoServico) {
        this.cartaoServico = cartaoServico;
    }

    @PostMapping
    public ResponseEntity<Cartao> adicionarCartao(@RequestBody CartaoDTO cartaoDto) {
        Cartao cartaoSalvo = cartaoServico.adicionarCartao(cartaoDto);
        return new ResponseEntity<>(cartaoSalvo, HttpStatus.CREATED);
    }
}