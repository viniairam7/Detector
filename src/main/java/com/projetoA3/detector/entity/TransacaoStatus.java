package com.projetoA3.detector.entity;

public enum TransacaoStatus {
    PENDING,   // Aguardando confirmação do usuário
    COMPLETED, // Aprovada (seja direto ou após confirmação)
    DENIED     // Negada pelo usuário
}