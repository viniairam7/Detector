package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;

public interface CartaoServico {

    /**
     * Adiciona um novo cartão a um usuário existente.
     * @param cartaoDto Os dados do cartão a ser adicionado.
     * @return O objeto Cartao que foi salvo no banco.
     */
    Cartao adicionarCartao(CartaoDTO cartaoDto);
}
