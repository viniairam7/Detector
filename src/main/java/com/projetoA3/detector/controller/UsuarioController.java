package com.projetoA3.detector.controller;

import com.projetoA3.detector.dto.UsuarioDTO;
import com.projetoA3.detector.entity.Usuario;
import com.projetoA3.detector.service.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServico UsuarioServico;

    @Autowired
    public UsuarioController(UsuarioServico usuarioServico) {
        this.UsuarioServico = usuarioServico;
    }

    @PostMapping
    public ResponseEntity<Usuario> criarUsuario(@RequestBody UsuarioDTO usuarioDto) {
        Usuario novoUsuario = new Usuario();
        novoUsuario.setNome(usuarioDto.getNome());
        novoUsuario.setEmail(usuarioDto.getEmail());
        novoUsuario.setSenha(usuarioDto.getSenha());
        
        Usuario usuarioSalvo = UsuarioServico.criarUsuario(novoUsuario);
        
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }
}