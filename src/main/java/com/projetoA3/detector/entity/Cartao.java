package com.projetoA3.detector.entity;

import com.fasterxml.jackson.annotation.JsonBackReference; // Importe esta
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference; // Importe esta
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero;

    @Column(nullable = false)
    private String validade;

    @Column(nullable = false)
    private String nomeTitular;

    // Apenas UMA declaração do campo 'usuario'
    @JsonBackReference // Com a anotação
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;
    
    // Apenas UMA declaração do campo 'transacoes'
    @JsonIgnore
    @JsonManagedReference // Com a anotação
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public Usuarios getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuarios usuario) {
        this.usuario = usuario;
    }

    public List<Transacao> getTransacoes() {
        return transacoes;
    }

    public void setTransacoes(List<Transacao> transacoes) {
        this.transacoes = transacoes;
    }
}