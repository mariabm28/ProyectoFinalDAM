package com.tienda.proyectofinal.controller;

import com.tienda.proyectofinal.model.Servicio;
import com.tienda.proyectofinal.service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@RequiredArgsConstructor
public class ServicioController {

    private final ServicioService servicioService;

    @GetMapping
    public List<Servicio> listar()  {
        return servicioService.listar(); }

    @PostMapping
    public Servicio guardar(@RequestBody @Valid Servicio s){
        return servicioService.guardar(s);}

    @DeleteMapping("/{id}")
    public void borrar(@PathVariable String id){
        servicioService.borrar(id);}

    @PutMapping("/{id}")
    public Servicio act(@PathVariable String id,@RequestBody @Valid Servicio s){
        if(!id.equals(s.getNombre())) throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,"PK no se puede cambiar");
        return servicioService.guardar(s);
    }
}
