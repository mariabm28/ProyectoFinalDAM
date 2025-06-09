package com.tienda.proyectofinal.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "citas")
@Data
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @NotNull
    private LocalDateTime fecha;

    @ManyToOne(optional = false)
    @JoinColumn(name = "servicio", referencedColumnName = "nombre")
    private Servicio servicio;


}