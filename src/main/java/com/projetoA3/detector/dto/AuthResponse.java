// Local: src/main/java/com/projetoA3/detector/dto/AuthResponse.java
package com.projetoA3.detector.dto;

import java.io.Serializable;

public class AuthResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}