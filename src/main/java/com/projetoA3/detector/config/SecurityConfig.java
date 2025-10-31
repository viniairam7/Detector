// Local: src/main/java/com/projetoA3/detector/configuracao/SecurityConfig.java
package com.projetoA3.detector.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; // Vamos criar este em breve

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        // Este Bean é o que o AuthController precisava
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Desabilita o CSRF (Cross-Site Request Forgery)
                .csrf(csrf -> csrf.disable())
                
                // Define as regras de autorização
                .authorizeHttpRequests(auth -> auth
                        // *** NOVO: Permite todas as requisições OPTIONS (pré-voo CORS) ***
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        
                        // Permite acesso público ao endpoint de login
                        .requestMatchers("/api/auth/login").permitAll()
                        // Permite acesso público ao endpoint de cadastro de usuário (POST em /api/usuarios)
                        .requestMatchers(HttpMethod.POST, "/api/usuarios").permitAll()
                        // Todas as outras requisições precisam estar autenticadas
                        .anyRequest().authenticated()
                )
                
                // ... (exceptionHandling e sessionManagement)
                
                // Configura o tratamento de exceção para falhas de autenticação
                .exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))

                // Configura a política de sessão para STATELESS (sem estado)
                // O JWT não usa sessões do lado do servidor
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Adiciona nosso filtro JWT antes do filtro padrão de usuário/senha do Spring
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}