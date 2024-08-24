package com.spring.springsecurityfinal.infrastructure.web.controller;


import com.spring.springsecurityfinal.application.dto.request.UsuarioEditRequest;
import com.spring.springsecurityfinal.application.dto.request.UsuarioRequest;
import com.spring.springsecurityfinal.application.dto.response.CustomPagedResponse;
import com.spring.springsecurityfinal.domain.entity.Usuario;
import com.spring.springsecurityfinal.domain.repository.UsuarioRepository;
import lombok.AllArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@AllArgsConstructor
public class UsuarioController {


    private final UsuarioRepository usuarioRepository;


    @GetMapping
    ResponseEntity<?> listUsuarios(@RequestParam(required = false,defaultValue = "10") Integer size,
                                   @RequestParam(required = false,defaultValue = "0") Integer page,
                                   PagedResourcesAssembler<Usuario> pagedResourcesAssembler){

        Pageable pageable= PageRequest.of(page,size);
        Page<Usuario> usuarioPage= usuarioRepository.listUsuarioPage(pageable);

        if (usuarioPage.isEmpty()){
            return new ResponseEntity<>(usuarioPage, HttpStatus.NO_CONTENT);
        }

        PagedModel<EntityModel<Usuario>> pagedModel = pagedResourcesAssembler.toModel(usuarioPage);

        List<EntityModel<Usuario>> content = pagedModel.getContent().stream().toList();
        PagedModel.PageMetadata pageMetadata = pagedModel.getMetadata();

        CustomPagedResponse<Usuario> response = new CustomPagedResponse<>(content, pageMetadata);

        return ResponseEntity.ok(response);
    }
    @PostMapping
    ResponseEntity<Usuario> createUsuarios(@Validated @RequestBody UsuarioRequest usuarioRequest){
        return  new ResponseEntity<>(usuarioRepository.createUsuario(usuarioRequest),HttpStatus.CREATED);
    }

    @GetMapping("/{usuarioId}")
    ResponseEntity<Usuario> getOneUsuario(@PathVariable Long usuarioId){
        return  ResponseEntity.ok(usuarioRepository.findByUusarioId(usuarioId));
    }

    @PutMapping("/{usuarioId}")
    ResponseEntity<Usuario> editUsuario(@PathVariable Long usuarioId,
                                        @Validated @RequestBody UsuarioEditRequest usuarioEditRequest){
        return ResponseEntity.ok(usuarioRepository.editUsuario(usuarioId,usuarioEditRequest));
    }


}
