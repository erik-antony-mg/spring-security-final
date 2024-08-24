package com.spring.springsecurityfinal.domain.repository;

import com.spring.springsecurityfinal.application.dto.request.UsuarioEditRequest;
import com.spring.springsecurityfinal.application.dto.request.UsuarioRequest;
import com.spring.springsecurityfinal.domain.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository {

    Page<Usuario> listUsuarioPage(Pageable pageable);

    Usuario createUsuario(UsuarioRequest usuarioRequest);

    Usuario findByUusarioId(Long usuarioId);

    Usuario editUsuario(Long usuarioId, UsuarioEditRequest usuarioEditRequest);
}
