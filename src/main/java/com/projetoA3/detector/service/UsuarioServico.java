package com.projetoA3.detector.service;

import com.projetoA3.detector.entity.Usuarios;
import java.util.List;

public interface UsuarioServico {

    Usuarios criarUsuario(Usuarios usuario);
    List<Usuarios> listarTodos();
    
}