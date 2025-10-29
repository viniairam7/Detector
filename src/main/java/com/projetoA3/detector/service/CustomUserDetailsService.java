// Local: src/main/java/com/projetoA3/detector/service/CustomUserDetailsService.java
package com.projetoA3.detector.service;

import com.projetoA3.detector.entity.Usuarios;
import com.projetoA3.detector.repository.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Busca o usuário no banco de dados pelo e-mail
        Usuarios usuario = usuarioRepositorio.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com o e-mail: " + email));

        // Converte nosso objeto 'Usuarios' para um objeto 'User' que o Spring Security entende.
        // Por enquanto, não estamos usando "roles" (perfis de acesso), então passamos uma lista vazia.
        return new User(usuario.getEmail(), usuario.getSenha(), new ArrayList<>());
    }
}