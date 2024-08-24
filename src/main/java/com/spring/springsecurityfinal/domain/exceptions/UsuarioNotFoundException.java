package com.spring.springsecurityfinal.domain.exceptions;

public class UsuarioNotFoundException extends RuntimeException{

    public UsuarioNotFoundException() {
        super();
    }

    public UsuarioNotFoundException(String message) {
        super(message);
    }
}
