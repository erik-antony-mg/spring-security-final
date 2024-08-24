package com.spring.springsecurityfinal.infrastructure.security.jwt.exceptions;

import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;



public class JwtExceptionCustom extends JwtException {
    private HttpStatus estado;
    private String mensaje;

    public JwtExceptionCustom(HttpStatus estado, String mensaje) {
        super(mensaje);
        this.estado = estado;
        this.mensaje = mensaje;
    }
}