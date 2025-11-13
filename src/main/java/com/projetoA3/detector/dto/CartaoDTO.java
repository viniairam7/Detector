package com.projetoA3.detector.dto;

import java.math.BigDecimal; // Importar

public class CartaoDTO {

    // --- CAMPO ID ADICIONADO ---
    // Necessário para o frontend (ex: <li key={cartao.id}>)
    private Long id; 
    
    private String numero;
    private String validade;
    private String nomeTitular;
    
    // --- NOVOS CAMPOS ADICIONADOS ---
    private String localizacaoPadrao;
    private BigDecimal gastoPadraoMensal;
    // --- FIM DOS NOVOS CAMPOS ---


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
}
