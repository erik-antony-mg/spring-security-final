package com.spring.springsecurityfinal.domain.exceptions;

public class EmailCustomException extends RuntimeException{
    public EmailCustomException(String message) {
        super(message);
    }

    public EmailCustomException() {
        super();
    }
}
