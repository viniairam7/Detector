package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.UsuarioDTO;
import com.projetoA3.detector.entity.HistoricoUsuario;
import com.projetoA3.detector.entity.UsuarioOmitido;
import com.projetoA3.detector.entity.Usuarios;
import java.util.List;
import java.util.Optional;

public interface UsuarioServico {

    Usuarios criarUsuario(Usuarios usuario);
    List<Usuarios> listarTodos();
    Optional<Usuarios> atualizarUsuario(Long id, UsuarioDTO usuarioDTO);
    List<HistoricoUsuario> listarHistorico(Long id);

    // NOVOS MÉTODOS PARA DELETE
    boolean omitirUsuario(Long id);
    List<UsuarioOmitido> listarOmitidos();
}