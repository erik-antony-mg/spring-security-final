package com.spring.springsecurityfinal.domain.repository;

import com.spring.springsecurityfinal.application.dto.request.AuthRequest;
import com.spring.springsecurityfinal.application.dto.response.AuthResponse;
import com.spring.springsecurityfinal.domain.entity.Usuario;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthRepository {

     AuthResponse login(AuthRequest authRequest);
     Usuario usuarioLogeado();
}
