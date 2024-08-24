package com.spring.springsecurityfinal.application.service;

import com.spring.springsecurityfinal.application.dto.request.AuthRequest;
import com.spring.springsecurityfinal.application.dto.response.AuthResponse;
import com.spring.springsecurityfinal.domain.entity.Usuario;
import com.spring.springsecurityfinal.domain.repository.AuthRepository;
import com.spring.springsecurityfinal.infrastructure.persistence.UsuarioPersistence;
import com.spring.springsecurityfinal.infrastructure.security.jwt.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService implements AuthRepository {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioPersistence usuarioPersistence;

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.email()
                ,authRequest.password()));

        Usuario usuario= usuarioPersistence.findUsuarioByEmail(authRequest.email()).get();
        String jwt= jwtService.generateToken(usuario);
        return new AuthResponse(jwt);
    }

    @Override
    public Usuario usuarioLogeado() {
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof UsernamePasswordAuthenticationToken authToken){
            String email= (String) authToken.getPrincipal();
            return usuarioPersistence.findUsuarioByEmail(email)
                    .orElseThrow(()-> new UsernameNotFoundException("usuario no encontrado con el email "+email));
        }
        return null;
    }
}
