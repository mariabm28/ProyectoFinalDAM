package com.tienda.proyectofinal.service;

import com.tienda.proyectofinal.model.Cita;
import com.tienda.proyectofinal.model.Servicio;
import com.tienda.proyectofinal.model.Usuario;
import com.tienda.proyectofinal.repository.CitaRepository;
import com.tienda.proyectofinal.repository.ServicioRepository;
import com.tienda.proyectofinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;
    @Autowired
    private ServicioRepository servicioRepository;


    public List<Cita> obtenerTodas() {
        List<Cita> citas = citaRepository.findAll();
        System.out.println("Citas encontradas: " + citas.size());
        return citas;
    }
    public Optional<Cita> obtenerPorId(Long id) {
        return citaRepository.findById(id);
    }



    public Cita guardar(Cita cita) {
        String nombre = cita.getServicio().getNombre();
        Servicio servicio = servicioRepository.findByNombre(nombre);


        if (servicio == null) {
            throw new RuntimeException("Servicio no encontrado: " + nombre);
        }

        return citaRepository.save(cita);
    }

    public void eliminar(Long id) {
        citaRepository.deleteById(id);
    }

    public Cita actualizar(Long id, Cita citaActualizada) {
        return citaRepository.findById(id).map(cita -> {
            cita.setFecha(citaActualizada.getFecha());
            cita.setServicio(citaActualizada.getServicio());
            cita.setUsuario(citaActualizada.getUsuario());
            return citaRepository.save(cita);
        }).orElseThrow(() -> new RuntimeException("Cita no encontrada con id: " + id));
    }

    public List<Cita> obtenerCitasPorUsuario(Long usuarioId) {
        return citaRepository.findByUsuarioId(usuarioId);
    }
}