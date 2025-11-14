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
     * Calcula a distância em KM entre dois pontos geográficos (lat/lon).
     * Usado para comparar a localização da Loja (Ponto A) com a do Usuário (Ponto B).
     */
    @Query(value = "SELECT ST_Distance(" +
                   "    ST_MakePoint(:lonPontoA, :latPontoA)::geography, " +
                   "    ST_MakePoint(:lonPontoB, :latPontoB)::geography " +
                   ") / 1000.0",
           nativeQuery = true)
    Double calcularDistanciaEntrePontosKm(
            @Param("lonPontoA") double lonPontoA,
            @Param("latPontoA") double latPontoA,
            @Param("lonPontoB") double lonPontoB,
            @Param("latPontoB") double latPontoB
    );
}
