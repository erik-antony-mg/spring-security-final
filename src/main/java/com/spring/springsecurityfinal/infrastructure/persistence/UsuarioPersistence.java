package com.spring.springsecurityfinal.infrastructure.persistence;

import com.spring.springsecurityfinal.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioPersistence extends JpaRepository<Usuario,Long> {

    Optional<Usuario> findUsuarioByEmail(String email);
    Optional<Usuario> findUsuarioByDni(String dni);


}
