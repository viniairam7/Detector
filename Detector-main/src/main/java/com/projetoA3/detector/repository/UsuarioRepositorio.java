package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// A anotação @Repository é opcional aqui, não é necessária.

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    /**
     * Busca um usuário pelo seu endereço de e-mail.
     * O Spring Data JPA cria a consulta automaticamente a partir do nome do método.
     * @param email O e-mail do usuário a ser pesquisado.
     * @return um Optional contendo o usuário se encontrado, ou vazio se não encontrado.
     */
    Optional<Usuarios> findByEmail(String email);

}