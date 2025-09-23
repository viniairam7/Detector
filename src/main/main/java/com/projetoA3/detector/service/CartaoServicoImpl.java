package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartaoServicoImpl implements CartaoServico {

    private final CartaoRepositorio cartaoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public CartaoServicoImpl(CartaoRepositorio cartaoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.cartaoRepositorio = cartaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @Override
    public Cartao adicionarCartao(CartaoDTO cartaoDTO) {
     
        Usuarios usuario = usuarioRepositorio.findById(cartaoDTO.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com o ID: " + cartaoDTO.getUsuarioId()));

        
        Cartao novoCartao = new Cartao();
        novoCartao.setNumero(cartaoDTO.getNumero());
        novoCartao.setValidade(cartaoDTO.getValidade());
        novoCartao.setNomeTitular(cartaoDTO.getNomeTitular());
        novoCartao.setUsuario(usuario);
        return cartaoRepositorio.save(novoCartao);
    }
} 

