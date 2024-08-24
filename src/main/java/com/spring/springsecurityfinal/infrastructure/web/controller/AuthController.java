package com.spring.springsecurityfinal.infrastructure.web.controller;

import com.spring.springsecurityfinal.application.dto.request.AuthRequest;
import com.spring.springsecurityfinal.application.dto.response.AuthResponse;
import com.spring.springsecurityfinal.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Validated AuthRequest authRequest){

        return ResponseEntity.ok(authService.login(authRequest));
    }

    @GetMapping("/profile")
    public ResponseEntity<?> profile(){
        return ResponseEntity.ok(authService.usuarioLogeado());
    }
}
