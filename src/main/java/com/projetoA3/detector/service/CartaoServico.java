package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import java.util.List;

public interface CartaoServico {

    /**
     * Adiciona um novo cartão a um usuário.
     * O usuário é identificado pelo seu e-mail (vindo do token).
     */
    Cartao adicionarCartao(CartaoDTO cartaoDto, String userEmail);

    /**
     * Lista todos os cartões (em formato DTO) de um usuário específico.
     * O usuário é identificado pelo seu e-mail (vindo do token).
     */
    List<CartaoDTO> buscarCartoesPorUsuarioEmail(String email);
}
