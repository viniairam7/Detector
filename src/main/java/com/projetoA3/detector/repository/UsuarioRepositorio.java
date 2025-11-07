package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // A importação que faltava
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuarios, Long> {

    
    Optional<Usuarios> findByEmail(String email);

    Optional<Usuarios> findByEmailAndAtivoTrue(String email);

    // NOVO MÉTODO PARA BUSCAR APENAS USUÁRIOS ATIVOS
    List<Usuarios> findByAtivoTrue();
}

