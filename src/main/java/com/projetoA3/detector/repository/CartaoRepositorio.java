package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepositorio extends JpaRepository<Cartao, Long> {

    // "Busque uma lista de Cartoes pelo Id do seu atributo 'usuario'"
    List<Cartao> findByUsuarioId(Long usuarioId);
}