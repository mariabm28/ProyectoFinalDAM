package com.tienda.proyectofinal.repository;


import com.tienda.proyectofinal.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {
    List<Cita> findByUsuarioId(Long usuarioId);
}
