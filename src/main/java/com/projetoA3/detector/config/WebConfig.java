package com.projetoA3.detector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Aplica o CORS para todos os endpoints /api/
            .allowedOrigins(
                "http://localhost:3000", // Permite o seu front-end local
                "https://*.ngrok-free.app" // Permite QUALQUER subdomínio do ngrok
            ) 
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
            .allowedHeaders("*")
            .allowCredentials(true); 
    }
}
