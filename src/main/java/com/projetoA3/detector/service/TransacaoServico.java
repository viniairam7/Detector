package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.entity.Transacao;

public interface TransacaoServico {

    /**
     * Registra uma nova transação para um cartão existente.
     * @param transacaoDto Os dados da transação a ser registrada.
     * @return A entidade Transacao que foi salva no banco.
     */
    Transacao registrarTransacao(TransacaoDTO transacaoDto);
}