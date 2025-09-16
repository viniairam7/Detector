package com.projetoA3.detector.service;

import com.projetoA3.detector.entity.Usuario;


public class UsuarioServico {

    private String nome;

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

     public Usuario criarUsuario(Usuario usuario) {
        return usuario; 
    }
}