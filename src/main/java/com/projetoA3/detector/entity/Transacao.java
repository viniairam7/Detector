package com.projetoA3.detector.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// Importação necessária para o tipo "Point" do PostGIS
import org.locationtech.jts.geom.Point;

@Entity
@Table(name = "transacoes")
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal valor;

    @Column(nullable = false)
    private String estabelecimento;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cartao_id", nullable = false)
    private Cartao cartao;

    // --- CAMPO ADICIONADO PARA POSTGIS ---
    /**
     * Armazena a geolocalização da transação (Latitude/Longitude).
     * O tipo "Point" é do PostGIS (via biblioteca JTS).
     * A "definition" especifica o tipo no banco e o SRID 4326 (padrão para GPS).
     */
    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point localizacao;

    // --- CAMPO ADICIONADO PARA IP ---
    @Column
    private String ipAddress;

    
    // --- Getters e Setters ---
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(String estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    // --- GETTERS E SETTERS PARA OS NOVOS CAMPOS ---

    public Point getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(Point localizacao) {
        this.localizacao = localizacao;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
}