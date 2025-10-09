package com.projetoA3.detector.service;

import com.projetoA3.detector.dto.UsuarioDTO;
import com.projetoA3.detector.entity.HistoricoUsuario;
import com.projetoA3.detector.entity.UsuarioOmitido;
import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.HistoricoUsuarioRepositorio;
import com.projetoA3.detector.repository.UsuarioOmitidoRepositorio;
import com.projetoA3.detector.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicoImpl implements UsuarioServico {

    private final UsuarioRepositorio usuarioRepositorio;
    private final HistoricoUsuarioRepositorio historicoUsuarioRepositorio;
    private final UsuarioOmitidoRepositorio usuarioOmitidoRepositorio;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioServicoImpl(UsuarioRepositorio usuarioRepositorio,
                              HistoricoUsuarioRepositorio historicoUsuarioRepositorio,
                              UsuarioOmitidoRepositorio usuarioOmitidoRepositorio,
                              PasswordEncoder passwordEncoder) {
        this.usuarioRepositorio = usuarioRepositorio;
        this.historicoUsuarioRepositorio = historicoUsuarioRepositorio;
        this.usuarioOmitidoRepositorio = usuarioOmitidoRepositorio;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Usuarios criarUsuario(Usuarios usuario) {
        Optional<Usuarios> usuarioExistente = usuarioRepositorio.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent()) {
            throw new IllegalArgumentException("Email já registado.");
        }
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);
        return usuarioRepositorio.save(usuario);
    }

    @Override
    public List<Usuarios> listarTodos() {
        return usuarioRepositorio.findByAtivoTrue();
    }

    @Override
    public List<HistoricoUsuario> listarHistorico(Long id) {
        return historicoUsuarioRepositorio.findByUsuarioIdOrderByDataModificacaoDesc(id);
    }

    @Override
    @Transactional
    public Optional<Usuarios> atualizarUsuario(Long id, UsuarioDTO usuarioDTO) {
        return usuarioRepositorio.findById(id).map(usuarioExistente -> {
            HistoricoUsuario historico = new HistoricoUsuario(usuarioExistente);
            historicoUsuarioRepositorio.save(historico);

            if (usuarioDTO.getNome() != null) {
                usuarioExistente.setNome(usuarioDTO.getNome());
            }
            if (usuarioDTO.getEmail() != null) {
                usuarioExistente.setEmail(usuarioDTO.getEmail());
            }
            if (usuarioDTO.getSenha() != null && !usuarioDTO.getSenha().isEmpty()) {
                usuarioExistente.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            }

            return usuarioRepositorio.save(usuarioExistente);
        });
    }

    @Override
    @Transactional
    public boolean omitirUsuario(Long id) {
        Optional<Usuarios> usuarioOpt = usuarioRepositorio.findById(id);
        if (usuarioOpt.isPresent()) {
            Usuarios usuario = usuarioOpt.get();

            UsuarioOmitido omitido = new UsuarioOmitido(usuario);
            usuarioOmitidoRepositorio.save(omitido);

            usuario.setAtivo(false);
            usuarioRepositorio.save(usuario);
            return true;
        }
        return false;
    }

    @Override
    public List<UsuarioOmitido> listarOmitidos() {
        return usuarioOmitidoRepositorio.findAll();
    }
}