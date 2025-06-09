package com.tienda.proyectofinal.controller;

import com.tienda.proyectofinal.model.Cita;
import com.tienda.proyectofinal.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @GetMapping
    public ResponseEntity<List<Cita>> getCitas() {
        List<Cita> citas = citaService.obtenerTodas();
        return ResponseEntity.ok(citas);
    }
    @PostMapping
    public ResponseEntity<Cita> agendarCita(@Valid @RequestBody Cita cita) {
        try {
            Cita nuevaCita = citaService.guardar(cita);
            if (nuevaCita == null) {
                return ResponseEntity.badRequest().build(); // algo salió mal
            }
            System.out.println("RESPUESTA CITA => " + nuevaCita);
            return ResponseEntity.ok(nuevaCita);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable Long id) {
        try {
            citaService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable Long id, @Valid @RequestBody Cita cita) {
        try {
            Cita actualizada = citaService.actualizar(id, cita);
            return ResponseEntity.ok(actualizada);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<List<Cita>> getCitasPorUsuario(@PathVariable("id") Long usuarioId) {
        List<Cita> citas = citaService.obtenerCitasPorUsuario(usuarioId);
        return ResponseEntity.ok(citas);
    }
}
