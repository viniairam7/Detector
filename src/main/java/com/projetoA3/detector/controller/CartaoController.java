package com.projetoA3.detector.controller;

import com.projetoA3.detector.dto.CartaoDTO;
import com.projetoA3.detector.entity.Cartao;
import com.projetoA3.detector.service.CartaoServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.security.Principal; // Importar Principal
import org.springframework.web.bind.annotation.*;
import java.util.List; // Importar List

@RestController
@RequestMapping("/api/cartoes")
@CrossOrigin // Adicionado para permitir acesso do frontend
public class CartaoController {

    private final CartaoServico cartaoServico;

    @Autowired
    public CartaoController(CartaoServico cartaoServico) {
        this.cartaoServico = cartaoServico;
    }

    // --- POST Endpoint (Adicionar Cartão) ---
    // Atualizado para receber 'Principal' e passá-lo para o serviço
    @PostMapping
    public ResponseEntity<Cartao> adicionarCartao(@RequestBody CartaoDTO cartaoDto, Principal principal) {
        // Obtém o email do usuário logado a partir do token (via Principal)
        String userEmail = principal.getName(); 
        
        // Chama o método de serviço atualizado
        Cartao cartaoSalvo = cartaoServico.adicionarCartao(cartaoDto, userEmail);
        return new ResponseEntity<>(cartaoSalvo, HttpStatus.CREATED);
    }

    // --- GET Endpoint (Listar Cartões) ---
    // Este é o endpoint que o DashboardPage.js chama
    // Corrigido para esperar e retornar List<CartaoDTO>
    @GetMapping("/meus-cartoes")
    public ResponseEntity<List<CartaoDTO>> getMeusCartoes(Principal principal) {
        String userEmail = principal.getName();
        
        // Este método agora retorna List<CartaoDTO>, o que é o correto
        List<CartaoDTO> cartoes = cartaoServico.buscarCartoesPorUsuarioEmail(userEmail); 
        
        return ResponseEntity.ok(cartoes);
    }
}
