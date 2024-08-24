package com.spring.springsecurityfinal.application.dto.errors;

import org.springframework.http.HttpStatusCode;

public record ErrorDetails(
        Integer codigo,
        String mensaje,
        HttpStatusCode httpStatus
) {
}
