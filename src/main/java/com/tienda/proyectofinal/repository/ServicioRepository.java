package com.tienda.proyectofinal.repository;

import com.tienda.proyectofinal.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioRepository extends JpaRepository<Servicio, String> {
    Servicio findByNombre(String nombre);
}
