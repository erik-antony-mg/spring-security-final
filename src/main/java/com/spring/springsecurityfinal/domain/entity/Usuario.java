package com.spring.springsecurityfinal.domain.entity;

import com.spring.springsecurityfinal.domain.enums.StatusName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usuarioId;
    private String nombre;
    private String apellido;
    private LocalDate fechaNacimiento;
    private String dni;
    private String celular;
    private String email;
    private String password;
    private String direccion;
    @Enumerated(EnumType.STRING)
    private StatusName status;
    private Integer edad;
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.PERSIST)
    @JoinTable(name = "usuarios_roles",
    joinColumns = @JoinColumn(name = "usuario_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @PrePersist
    public void guardarDatos(){
        if (status ==null){
            status=StatusName.HABILITADO;
        }
        if (edad == null){
            edad= Period.between(fechaNacimiento,LocalDate.now()).getYears();
        }
    }
}
