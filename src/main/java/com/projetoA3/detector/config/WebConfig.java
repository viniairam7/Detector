package com.projetoA3.detector.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // O método addCorsMappings(CorsRegistry registry) foi removido daqui
    // para evitar conflito com o SecurityConfig.
    
}