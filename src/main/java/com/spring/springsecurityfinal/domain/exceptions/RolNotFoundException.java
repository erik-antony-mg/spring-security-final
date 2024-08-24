package com.spring.springsecurityfinal.domain.exceptions;

public class RolNotFoundException extends RuntimeException{
    public RolNotFoundException() {
        super();
    }

    public RolNotFoundException(String message) {
        super(message);
    }
}
