package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {

    // "Busque uma lista de Transacoes pelo Id do seu atributo 'cartao'
    // e ordene pela 'dataHora' de forma descendente (a mais recente primeiro)"
    List<Transacao> findByCartaoIdOrderByDataHoraDesc(Long cartaoId);
}