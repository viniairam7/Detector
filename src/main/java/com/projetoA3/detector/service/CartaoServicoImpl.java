package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<CartaoDTO> buscarCartoesPorUsuarioEmail(String email) {
        // 1. Busca as entidades
        List<Cartao> cartoes = cartaoRepositorio.findByUsuarioEmail(email);
        
        // 2. Mapeia a lista de Entidades para a lista de DTOs
        return cartoes.stream()
                .map(this::convertToDto) // Chama o método auxiliar abaixo
                .collect(Collectors.toList());
    }

    @Override
public Cartao adicionarCartao(CartaoDTO cartaoDTO, String emailUsuarioLogado) {
    Usuarios usuario = usuarioRepositorio.findByEmailAndAtivoTrue(emailUsuarioLogado) // Use o método corrigido
            .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado: " + emailUsuarioLogado));

        
        Cartao novoCartao = new Cartao();
        novoCartao.setNumero(cartaoDTO.getNumero());
        novoCartao.setValidade(cartaoDTO.getValidade());
        novoCartao.setNomeTitular(cartaoDTO.getNomeTitular());
        novoCartao.setUsuario(usuario);
        return cartaoRepositorio.save(novoCartao);
    }


    private CartaoDTO convertToDto(Cartao cartao) {
        CartaoDTO dto = new CartaoDTO();
        
        dto.setId(cartao.getId()); // <-- CORRIGIDO (agora existe no DTO)
        dto.setNumero(cartao.getNumero());
        dto.setValidade(cartao.getValidade()); // <-- CORRIGIDO (era getDataValidade)
        dto.setNomeTitular(cartao.getNomeTitular());
        // A linha do 'limite' foi removida pois o campo não existe

        return dto;
    }

} 

