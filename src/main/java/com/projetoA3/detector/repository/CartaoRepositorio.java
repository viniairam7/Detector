package com.projetoA3.detector.repository;

import com.projetoA3.detector.entity.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query; // Importar Query
import org.springframework.data.repository.query.Param; // Importar Param
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartaoRepositorio extends JpaRepository<Cartao, Long> {

    List<Cartao> findByUsuarioId(Long usuarioId);

    // --- MÉTODO ATUALIZADO ---
    // Tornamos a consulta explícita com @Query para garantir que funciona
    @Query("SELECT c FROM Cartao c WHERE c.usuario.email = :email")
    List<Cartao> findByUsuarioEmail(@Param("email") String email);
}
