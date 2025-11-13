package com.projetoA3.detector.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.math.BigDecimal; // Importar BigDecimal
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

    // --- NOVOS CAMPOS ADICIONADOS ---
    @Column(nullable = true) // Permitir nulo por enquanto
    private String localizacaoPadrao; // Ex: "São Paulo, SP"

    @Column(nullable = true, precision = 10, scale = 2) // Permitir nulo
    private BigDecimal gastoPadraoMensal; // Ex: 1500.00
    // --- FIM DOS NOVOS CAMPOS ---

    @JsonBackReference // Anotação mantida
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuarios usuario;
    
    // @JsonIgnore removido (estava em conflito com @JsonManagedReference)
    @JsonManagedReference // Anotação mantida
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    // --- GETTERS E SETTERS ---

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

    // --- GETTERS E SETTERS PARA NOVOS CAMPOS ---
    public String getLocalizacaoPadrao() {
        return localizacaoPadrao;
    }

    public void setLocalizacaoPadrao(String localizacaoPadrao) {
        this.localizacaoPadrao = localizacaoPadrao;
    }

    public BigDecimal getGastoPadraoMensal() {
        return gastoPadraoMensal;
    }

    public void setGastoPadraoMensal(BigDecimal gastoPadraoMensal) {
        this.gastoPadraoMensal = gastoPadraoMensal;
    }
}
