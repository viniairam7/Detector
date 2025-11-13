package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {

    /**
     * Busca as transações de um cartão, ordenadas da mais recente para a mais antiga.
     */
    List<Transacao> findByCartaoIdOrderByDataHoraDesc(Long cartaoId);

    // --- MÉTODO ADICIONADO ---
    // Esta consulta é necessária para a página de "Ver Extrato".
    // Ela garante que só listamos transações se o ID do cartão pertencer
    // ao utilizador que está logado (identificado pelo email).
    @Query("SELECT t FROM Transacao t WHERE t.cartao.id = :cartaoId AND t.cartao.usuario.email = :email ORDER BY t.dataHora DESC")
    List<Transacao> findByCartaoIdAndCartaoUsuarioEmailOrderByDataHoraDesc(
        @Param("cartaoId") Long cartaoId, 
        @Param("email") String email
    );
    // --- FIM DO MÉTODO ADICIONADO ---

    /**
     * Calcula a distância em quilômetros (KM) entre duas transações usando PostGIS.
     * (O seu código PostGIS foi mantido!)
     */
    @Query(value = "SELECT ST_Distance(" +
                   "    (SELECT localizacao FROM transacoes WHERE id = :idTransacaoAtual)::geography, " +
                   "    (SELECT localizacao FROM transacoes WHERE id = :idTransacaoAnterior)::geography " +
                   ") / 1000.0",
           nativeQuery = true)
    Double calcularDistanciaEmKm(
            @Param("idTransacaoAtual") Long idTransacaoAtual,
            @Param("idTransacaoAnterior") Long idTransacaoAnterior
    );
}
}
