package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

    // O Spring Data JPA cria a consulta automaticamente a partir do nome do método
    // "Busque um Usuario pelo seu atributo 'email'"
    Optional<Usuario> findByEmail(String email);
}