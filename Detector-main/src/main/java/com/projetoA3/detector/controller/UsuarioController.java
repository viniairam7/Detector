package com.projetoA3.detector.controller;

import com.projetoA3.detector.dto.UsuarioDTO;
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.service.UsuarioServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List; // <-- ESTA É A LINHA QUE FALTAVA

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioServico usuarioServico;

    @Autowired
    public UsuarioController(UsuarioServico usuarioServico) {
        this.usuarioServico = usuarioServico;
    }

    @PostMapping
    public ResponseEntity<Usuarios> criarUsuario(@RequestBody UsuarioDTO usuarioDto) {
        Usuarios novoUsuario = new Usuarios();
        novoUsuario.setNome(usuarioDto.getNome());
        novoUsuario.setEmail(usuarioDto.getEmail());
        novoUsuario.setSenha(usuarioDto.getSenha());
        
        Usuarios usuarioSalvo = usuarioServico.criarUsuario(novoUsuario);
        
        return new ResponseEntity<>(usuarioSalvo, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Usuarios>> listarTodosUsuarios() {
        List<Usuarios> usuarios = usuarioServico.listarTodos();
        return ResponseEntity.ok(usuarios);
    }
}