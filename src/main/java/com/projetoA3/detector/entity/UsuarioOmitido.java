package com.projetoA3.detector.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios_omitidos")
public class UsuarioOmitido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long usuarioId; // ID do usuário original

    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private LocalDateTime dataOmissao;

    // Construtor vazio
    public UsuarioOmitido() {
    }

    // Construtor para facilitar a criação a partir de um usuário
    public UsuarioOmitido(Usuarios usuario) {
        this.usuarioId = usuario.getId();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataOmissao = LocalDateTime.now();
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getDataOmissao() {
        return dataOmissao;
    }

    public void setDataOmissao(LocalDateTime dataOmissao) {
        this.dataOmissao = dataOmissao;
    }
}
