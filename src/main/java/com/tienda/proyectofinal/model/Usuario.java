package com.tienda.proyectofinal.model;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nombre;

    @Email
    @NotBlank
    @Column(unique = true)
    private String email;

    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres" )
    private String password;

    @NotBlank
    private String rol;

}