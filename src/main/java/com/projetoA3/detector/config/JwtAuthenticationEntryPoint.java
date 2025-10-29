// Local: src/main/java/com/projetoA3/detector/configuracao/JwtAuthenticationEntryPoint.java
package com.projetoA3.detector.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    private static final long serialVersionUID = -7858869558953243875L;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Envia um erro 401 (Não Autorizado) quando a autenticação falha
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
    }
}