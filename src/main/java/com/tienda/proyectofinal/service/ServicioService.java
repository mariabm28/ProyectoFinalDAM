package com.tienda.proyectofinal.service;

import com.tienda.proyectofinal.model.Servicio;
import com.tienda.proyectofinal.repository.ServicioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioService {

    private final ServicioRepository repo;

    public ServicioService(ServicioRepository repo) {
        this.repo = repo;
    }

    public List<Servicio> listar()            {
        return repo.findAll(); }

    public Servicio obtener(String nombre)    {
        return repo.findById(nombre).orElseThrow(); }

    public Servicio guardar(Servicio s)       {
        return repo.save(s); }

    public void borrar(String nombre)         {
        repo.deleteById(nombre); }
}

