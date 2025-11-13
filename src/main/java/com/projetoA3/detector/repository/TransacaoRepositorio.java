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

    /**
     * Calcula a distância em quilômetros (KM) entre duas transações usando PostGIS.
     * ST_Distance(geography, geography) calcula a distância em METROS.
     * Dividimos por 1000 para converter para KM.
     * Usamos 'geography' para cálculos precisos de lat/lon na Terra.
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
