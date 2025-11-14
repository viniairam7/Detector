package com.projetoA3.detector.dto;

import com.projetoA3.detector.entity.Transacao;

// DTO para a RESPOSTA do registro de transação
public class TransacaoResponseDTO {
    
    // Status para o Frontend: "COMPLETED" ou "PENDING_CONFIRMATION"
    private String statusResposta; 
    
    // Mensagem a ser exibida (Ex: "Fraude detectada...")
    private String mensagem;
    
    // A transação que foi criada (com seu ID e status PENDING)
    private Transacao transacao;

    public TransacaoResponseDTO(String statusResposta, String mensagem, Transacao transacao) {
        this.statusResposta = statusResposta;
        this.mensagem = mensagem;
        this.transacao = transacao;
    }

    // Getters e Setters
    public String getStatusResposta() {
        return statusResposta;
    }

    public void setStatusResposta(String statusResposta) {
        this.statusResposta = statusResposta;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }
}