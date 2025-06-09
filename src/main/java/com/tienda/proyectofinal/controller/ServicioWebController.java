package com.tienda.proyectofinal.controller;


import com.tienda.proyectofinal.model.Servicio;
import com.tienda.proyectofinal.service.ServicioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicios")
@RequiredArgsConstructor
public class ServicioWebController {

    private final ServicioService servicioService;

    @GetMapping
    public String lista(Model model){
        model.addAttribute("servicios", servicioService.listar());
        return "servicios";
    }


    @GetMapping("/nuevo")
    public String nuevo(Model m){
        m.addAttribute("servicio", new Servicio()); m.addAttribute("modo","crear");
        return "servicio-form"; }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable String id, Model m){
        m.addAttribute("servicio", servicioService.obtener(id));
        m.addAttribute("modo","editar"); return "servicio-form";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute("servicio") @Valid Servicio s, BindingResult br, Model m){
        if(br.hasErrors()){
            m.addAttribute("modo", s.getPrecio()==null ? "crear":"editar");
            return "servicio-form";
        }
        servicioService.guardar(s);
        return "redirect:/servicios";
    }

    @GetMapping("/borrar/{id}")
    public String borrar(@PathVariable String id){ servicioService.borrar(id); return "redirect:/servicios"; }
}
