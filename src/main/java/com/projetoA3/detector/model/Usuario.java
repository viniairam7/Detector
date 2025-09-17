package com.projetoA3.detector.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity // Avisa ao JPA que esta classe é uma tabela no banco
@Table(name = "usuarios") // Define o nome da tabela
public class Usuario {

    @Id // Marca o campo como chave primária
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura o auto-incremento
    private Long id;

    private String nome;
    private String email;
    private String senha; // Lembre-se que salvaremos a senha criptografada

    // Construtores, Getters e Setters
    // (Você pode gerar com o botão direito -> Source Action -> Generate Getters and Setters)

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
}