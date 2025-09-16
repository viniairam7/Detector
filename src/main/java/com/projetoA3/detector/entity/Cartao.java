package com.projetoA3.detector.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "cartoes")
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String numero; // Armazenaremos apenas os 4 últimos dígitos por segurança

    @Column(nullable = false)
    private String validade;

    @Column(nullable = false)
    private String nomeTitular;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    
    @OneToMany(mappedBy = "cartao", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transacao> transacoes;

    // Getters e Setters
    // ...
}