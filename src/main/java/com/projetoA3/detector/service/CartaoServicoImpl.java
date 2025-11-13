package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.CartaoRepositorio;
import com.projetoA3.detector.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // Importar Collectors

@Service
public class CartaoServicoImpl implements CartaoServico { // Implementa a interface correta

    private final CartaoRepositorio cartaoRepositorio;
    private final UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public CartaoServicoImpl(CartaoRepositorio cartaoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.cartaoRepositorio = cartaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    // --- MÉTODO 1: ADICIONAR CARTÃO ---
    // Atualizado para incluir os novos campos
    @Override
    public Cartao adicionarCartao(CartaoDTO cartaoDTO, String emailUsuarioLogado) {
        Usuarios usuario = usuarioRepositorio.findByEmail(emailUsuarioLogado) 
                .orElseThrow(() -> new RuntimeException("Usuário logado não encontrado: " + emailUsuarioLogado));
        
        Cartao novoCartao = new Cartao();
        novoCartao.setNumero(cartaoDTO.getNumero());
        novoCartao.setValidade(cartaoDTO.getValidade());
        novoCartao.setNomeTitular(cartaoDTO.getNomeTitular());
        novoCartao.setUsuario(usuario);
        
        // --- ADICIONA OS NOVOS CAMPOS ---
        novoCartao.setLocalizacaoPadrao(cartaoDTO.getLocalizacaoPadrao());
        novoCartao.setGastoPadraoMensal(cartaoDTO.getGastoPadraoMensal());
        // --- FIM DA ADIÇÃO ---
        
        return cartaoRepositorio.save(novoCartao);
    }

    // --- MÉTODO 2: BUSCAR CARTÕES ---
    // Corrigido para retornar List<CartaoDTO>
    @Override
    public List<CartaoDTO> buscarCartoesPorUsuarioEmail(String email) {
        // 1. Busca as entidades
        List<Cartao> cartoes = cartaoRepositorio.findByUsuarioEmail(email);
        
        // 2. Mapeia a lista de Entidades para a lista de DTOs
        return cartoes.stream()
                .map(this::convertToDto) // Chama o método auxiliar abaixo
                .collect(Collectors.toList());
    }

    // --- MÉTODO AUXILIAR PARA CONVERSÃO ---
    // Converte a Entidade Cartao para o CartaoDTO
    private CartaoDTO convertToDto(Cartao cartao) {
        CartaoDTO dto = new CartaoDTO();
        
        dto.setId(cartao.getId()); // O ID que adicionámos ao DTO
        dto.setNumero(cartao.getNumero());
        dto.setValidade(cartao.getValidade());
        dto.setNomeTitular(cartao.getNomeTitular());
        
        // --- ADICIONA OS NOVOS CAMPOS AO DTO ---
        dto.setLocalizacaoPadrao(cartao.getLocalizacaoPadrao());
        dto.setGastoPadraoMensal(cartao.getGastoPadraoMensal());
        // --- FIM DA ADIÇÃO ---

        return dto;
    }
}
