package com.spring.springsecurityfinal.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;


import java.util.List;

public record UsuarioRequest(
        @NotNull(message = "el campo nombre no debe estar vacio !!")
        String nombre,
        @NotNull(message = "el campo apellido no debe estar vacio !!")
        String apellido,
        @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "La fecha y hora deben tener el formato yyyy-MM-dd")
        @NotNull(message = "el campo fechaNacimiento no debe estar vacio !!")
        String fechaNacimiento,
        @Pattern(regexp = "^\\d{8}$",message = "el dni acepta 8 digitos")
        @NotNull(message = "el campo dni no debe estar vacio !!")
        String dni,
        @Pattern(regexp="^\\+\\d{1,3} \\d{9}$",message = "el celular tiene este formato +51 987654321")
        @NotNull(message = "el campo celular no debe estar vacio !!")
        String celular,
        @Pattern(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",message = "debe tener el formato example@gmail.com")
        @NotNull(message = "el campo email no debe estar vacio !!")
        String email,
        @NotNull(message = "el campo password no debe estar vacio !!")
        String password,
        @NotNull(message = "el campo direccion no debe estar vacio !!")
        String direccion,
        @Valid
        List<RolesRequest> listaRoles
) {
    public record RolesRequest(
            @NotNull(message = "el campo rolename no debe estar vacio !!")
            String roleName
    ){}
}
