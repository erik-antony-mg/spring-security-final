package com.spring.springsecurityfinal.application.dto.request;


import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull(message = "el campo email no debe estar vacio !!")
        String  email,
        @NotNull(message = "el campo password no debe estar vacio !!")
        String password
) {
}
