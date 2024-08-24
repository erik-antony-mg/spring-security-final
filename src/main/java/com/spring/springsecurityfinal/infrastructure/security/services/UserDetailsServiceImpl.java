package com.spring.springsecurityfinal.infrastructure.security.services;

import com.spring.springsecurityfinal.domain.entity.Usuario;
import com.spring.springsecurityfinal.domain.exceptions.UsuarioNotFoundException;
import com.spring.springsecurityfinal.infrastructure.persistence.UsuarioPersistence;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsuarioPersistence usuarioPersistence;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Usuario usuario= usuarioPersistence.findUsuarioByEmail(email)
                .orElseThrow(()-> new UsuarioNotFoundException("usuario con el "+email+" no encontrado"));

        List<SimpleGrantedAuthority> simpleGrantedAuthorityList=new ArrayList<>();
        usuario.getRoles()
                .forEach(rol -> simpleGrantedAuthorityList
                        .add(new SimpleGrantedAuthority("ROLE_".concat(rol.getRoleName().name()))));
        return new User(usuario.getEmail(),
                usuario.getPassword(),
                true,
                true,
                true,
                true,
                simpleGrantedAuthorityList);
    }
}
