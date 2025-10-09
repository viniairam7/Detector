package com.projetoA3.detector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Desabilita a proteção CSRF. Essencial para permitir requisições POST 
            // de clientes como Postman ou arquivos .http.
            .csrf(AbstractHttpConfigurer::disable) 
            .authorizeHttpRequests(authorize -> authorize
              
                // AQUI ESTÁ A MUDANÇA:
                // Permite TODAS as requisições (GET, POST, PUT, DELETE, etc.) 
                // para qualquer endpoint que comece com /api/
                .requestMatchers("/api/**").permitAll() 
                
                // Exige autenticação para qualquer outra requisição (se houver no futuro)
                .anyRequest().authenticated()
            );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
