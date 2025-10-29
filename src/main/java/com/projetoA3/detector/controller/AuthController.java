// Local: src/main/java/com/projetoA3/detector/controller/AuthController.java
package com.projetoA3.detector.controller;

import com.projetoA3.detector.dto.AuthRequest;
import com.projetoA3.detector.dto.AuthResponse;
import com.projetoA3.detector.service.CustomUserDetailsService;
import com.projetoA3.detector.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin // Permite que o frontend (rodando em localhost:3000) acesse esta API
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {

        // O 'authenticate' vai automaticamente usar o CustomUserDetailsService e o PasswordEncoder
        // para verificar se o e-mail e a senha são válidos.
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(), authRequest.getSenha()
            ));
        } catch (BadCredentialsException e) {
            throw new Exception("Email ou senha inválidos", e);
        } catch (DisabledException e) {
            throw new Exception("Usuário desabilitado", e);
        }

        // Se a autenticação foi bem-sucedida, carregamos os dados do usuário
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // Geramos o token JWT para este usuário
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Retornamos o token em uma resposta OK
        return ResponseEntity.ok(new AuthResponse(token));
    }
}