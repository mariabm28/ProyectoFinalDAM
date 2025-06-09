package com.tienda.proyectofinal.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "servicios")
@Data
public class Servicio {

    @Id
    @Column(length = 100)
    private String nombre;

    @Positive
    private Double precio;

    @Positive
    private Integer tiempo;
}
