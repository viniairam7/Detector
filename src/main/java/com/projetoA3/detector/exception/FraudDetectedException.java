// CustomException.java - Conteúdo exemplo
package com.projetoA3.detector.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// Esta anotação já faz o Spring retornar 400 Bad Request automaticamente
@ResponseStatus(HttpStatus.BAD_REQUEST) 
public class FraudDetectedException extends RuntimeException {
    public FraudDetectedException(String message) {
        super(message);
    }
}