package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.TransacaoDTO;
import com.projetoA3.detector.dto.TransacaoResponseDTO; // <-- IMPORTAR
import com.projetoA3.detector.entity.Transacao;
// import java.util.Optional; // <-- (Opcional, mas boa prática importar)

public interface TransacaoServico {

    /**
     * Registra uma nova transação.
     * Agora retorna um DTO de resposta que pode indicar um status PENDENTE.
     */
    TransacaoResponseDTO registrarTransacao(TransacaoDTO transacaoDto); // <-- TIPO DE RETORNO ATUALIZADO

    // --- NOVOS MÉTODOS PARA CONFIRMAÇÃO ---
    
    /**
     * Confirma uma transação que estava pendente.
     * @param transacaoId O ID da transação pendente.
     * @return A transação atualizada com status COMPLETED.
     */
    Transacao confirmarTransacao(Long transacaoId);

    /**
     * Nega (cancela) uma transação que estava pendente.
     * @param transacaoId O ID da transação pendente.
     * @return A transação atualizada com status DENIED.
     */
    Transacao negarTransacao(Long transacaoId);
}