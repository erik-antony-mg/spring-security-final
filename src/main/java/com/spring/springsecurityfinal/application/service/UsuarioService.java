package com.spring.springsecurityfinal.application.service;

import com.spring.springsecurityfinal.application.dto.request.UsuarioEditRequest;
import com.spring.springsecurityfinal.application.dto.request.UsuarioRequest;
import com.spring.springsecurityfinal.domain.entity.Role;
import com.spring.springsecurityfinal.domain.entity.Usuario;
import com.spring.springsecurityfinal.domain.enums.RoleName;
import com.spring.springsecurityfinal.domain.exceptions.DniCustomException;
import com.spring.springsecurityfinal.domain.exceptions.EmailCustomException;

import com.spring.springsecurityfinal.domain.exceptions.RolNotFoundException;
import com.spring.springsecurityfinal.domain.exceptions.UsuarioNotFoundException;
import com.spring.springsecurityfinal.domain.repository.UsuarioRepository;
import com.spring.springsecurityfinal.infrastructure.persistence.RolePersistence;
import com.spring.springsecurityfinal.infrastructure.persistence.UsuarioPersistence;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UsuarioService implements UsuarioRepository {

    private final UsuarioPersistence usuarioPersistence;
    private final RolePersistence rolePersistence;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<Usuario> listUsuarioPage(Pageable pageable) {
        return usuarioPersistence.findAll(pageable);
    }

    @Transactional
    @Override
    public Usuario createUsuario(UsuarioRequest usuarioRequest) {
        List<Role> listRoles=new ArrayList<>();

        if (usuarioRequest.listaRoles()==null){
           Role role = rolePersistence.findRoleByRoleName(RoleName.VISITANTE).get();
            listRoles.add(role);
        }else {
            usuarioRequest.listaRoles()
                    .forEach(rol-> {
                        RoleName roleName = RoleName.valueOf(rol.roleName().toUpperCase());
                            Role role = rolePersistence.findRoleByRoleName(roleName)
                                    .orElseThrow(()-> new RolNotFoundException("El rol con el nombre "+rol.roleName()+ "no existe !!"));
                            listRoles.add(role);
                    });
        }
        Usuario usuario= Usuario.builder()
                .email(usuarioRequest.email())
                .dni(usuarioRequest.dni())
                .nombre(usuarioRequest.nombre())
                .apellido(usuarioRequest.apellido())
                .celular(usuarioRequest.celular())
                .direccion(usuarioRequest.direccion())
                .fechaNacimiento(LocalDate.parse(usuarioRequest.fechaNacimiento()))
                .password(passwordEncoder.encode(usuarioRequest.password()))
                .roles(listRoles)
                .build();
        return usuarioPersistence.save(usuario);
    }

    @Override
    public Usuario findByUusarioId(Long usuarioId) {

        return usuarioPersistence.findById(usuarioId)
                .orElseThrow(()-> new UsuarioNotFoundException("Usuario no encontrado con el id "+usuarioId));
    }

    @Transactional
    @Override
    public Usuario editUsuario(Long usuarioId, UsuarioEditRequest usuarioEditRequest) {
        Usuario usuarioBd = usuarioPersistence.findById(usuarioId)
                .orElseThrow(() -> new UsuarioNotFoundException("Usuario no encontrado con el id " + usuarioId));

        Optional.ofNullable(usuarioEditRequest.nombre()).ifPresent(usuarioBd::setNombre);
        Optional.ofNullable(usuarioEditRequest.apellido()).ifPresent(usuarioBd::setApellido);

        if (usuarioEditRequest.email() != null) {
            if (usuarioEditRequest.email().equals(usuarioBd.getEmail())){
                throw new EmailCustomException("No puedes colocar el mismo Email !!!");
            }
            boolean emailExists = usuarioPersistence.findUsuarioByEmail(usuarioEditRequest.email()).isPresent();
            if (emailExists) {
                throw new EmailCustomException("Email ya registrado, ingrese otro !!!");
            }
            usuarioBd.setEmail(usuarioEditRequest.email());
        }

        if (usuarioEditRequest.dni() != null) {
            if (usuarioEditRequest.dni().equals(usuarioBd.getDni())){
                throw new DniCustomException("No puedes colocar el mismo DNI !!!");
            }
            boolean dniExists = usuarioPersistence.findUsuarioByDni(usuarioEditRequest.dni()).isPresent();
            if (dniExists) {
                throw new DniCustomException("DNI ya registrado, ingrese otro !!!");
            }
            usuarioBd.setDni(usuarioEditRequest.dni());
        }

        Optional.ofNullable(usuarioEditRequest.direccion()).ifPresent(usuarioBd::setDireccion);
        Optional.ofNullable(usuarioEditRequest.celular()).ifPresent(usuarioBd::setCelular);
        Optional.ofNullable(usuarioEditRequest.fechaNacimiento())
                .map(LocalDate::parse)
                .ifPresent(usuarioBd::setFechaNacimiento);
        Optional.ofNullable(usuarioEditRequest.password())
                .ifPresent(password -> usuarioBd.setPassword(passwordEncoder.encode(usuarioEditRequest.password())));


        if (usuarioEditRequest.listaRoles() != null) {
            List<Role> listRoles = usuarioEditRequest.listaRoles().stream()
                    .map(rol -> rolePersistence.findRoleByRoleName(RoleName.valueOf(rol.roleName().toUpperCase()))
                            .orElseThrow(() -> new RolNotFoundException("El rol con el nombre " + rol.roleName() + " no existe !!")))
                    .collect(Collectors.toList());
            usuarioBd.setRoles(listRoles);
        }

        return usuarioPersistence.save(usuarioBd);
    }

}
