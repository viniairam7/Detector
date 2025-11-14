package com.projetoA3.detector.entity;

import com.fasterxml.jackson.annotation.JsonIgnore; // <-- IMPORTE ESTA LINHA
import jakarta.persistence.*;
import java.util.List;
import java.math.BigDecimal; // <-- IMPORTAR
import java.time.LocalTime; // <-- IMPORTAR

@Entity
@Table(name = "usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column
    private BigDecimal mediaGasto; // Média de valor por transação

    @Column
    private int totalTransacoesParaMedia; // Contador para ajudar no cálculo da média

    @Column
    private LocalTime horarioHabitualInicio; // Ex: 08:00

    @Column
    private LocalTime horarioHabitualFim; // Ex: 22:00

    // A anotação @JsonManagedReference foi trocada por @JsonIgnore
    // para quebrar o loop de serialização de forma mais simples
    @JsonIgnore // <-- ADICIONE ESTA LINHA
    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Cartao> cartoes;

    // Campo para o soft delete
    @Column(nullable = false)
    private boolean ativo = true;

    // Getters e Setters (Todos incluídos)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenha() {
        return senha;
    }

    public List<Cartao> getCartoes() {
        return cartoes;
    }

    public void setCartoes(List<Cartao> cartoes) {
        this.cartoes = cartoes;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public BigDecimal getMediaGasto() {
        return mediaGasto;
    }

    public void setMediaGasto(BigDecimal mediaGasto) {
        this.mediaGasto = mediaGasto;
    }

    public int getTotalTransacoesParaMedia() {
        return totalTransacoesParaMedia;
    }

    public void setTotalTransacoesParaMedia(int totalTransacoesParaMedia) {
        this.totalTransacoesParaMedia = totalTransacoesParaMedia;
    }

    public LocalTime getHorarioHabitualInicio() {
        return horarioHabitualInicio;
    }

    public void setHorarioHabitualInicio(LocalTime horarioHabitualInicio) {
        this.horarioHabitualInicio = horarioHabitualInicio;
    }

    public LocalTime getHorarioHabitualFim() {
        return horarioHabitualFim;
    }

    public void setHorarioHabitualFim(LocalTime horarioHabitualFim) {
        this.horarioHabitualFim = horarioHabitualFim;
    }
}