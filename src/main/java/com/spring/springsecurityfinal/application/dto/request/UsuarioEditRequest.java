package com.spring.springsecurityfinal.application.dto.request;


import jakarta.validation.constraints.Pattern;

import java.util.List;

public record UsuarioEditRequest(
        String nombre,
        String apellido,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha y hora deben tener el formato yyyy-MM-dd")
        String fechaNacimiento,
        @Pattern(regexp = "^\\d{8}$",message = "el dni acepta 8 digitos")
        String dni,
        @Pattern(regexp="^\\+\\d{1,3} \\d{9}$",message = "el celular tiene este formato +51 987654321")
        String celular,
        @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "debe tener el formato example@gmail.com")
        String email,
        String password,
        String direccion,
        List<UsuarioRequest.RolesRequest> listaRoles
) {
    public record RolesRequest(
            String roleName
    ){}
}
