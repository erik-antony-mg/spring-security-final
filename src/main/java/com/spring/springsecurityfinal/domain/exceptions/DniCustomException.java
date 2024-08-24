package com.spring.springsecurityfinal.domain.exceptions;

public class DniCustomException extends RuntimeException{

    public DniCustomException() {
        super();
    }

    public DniCustomException(String message) {
        super(message);
    }
}
