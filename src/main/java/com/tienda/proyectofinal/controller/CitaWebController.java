package com.tienda.proyectofinal.controller;


import com.tienda.proyectofinal.model.Cita;
import com.tienda.proyectofinal.service.CitaService;
import com.tienda.proyectofinal.service.ServicioService;
import com.tienda.proyectofinal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/citas")
public class CitaWebController {

    private final CitaService      citaService;
    private final UsuarioService   usuarioService;
    private final ServicioService  servicioService;

    public CitaWebController(CitaService citaService,
                             UsuarioService usuarioService,
                             ServicioService servicioService) {
        this.citaService     = citaService;
        this.usuarioService  = usuarioService;
        this.servicioService = servicioService;
    }

    /* ---------- LISTA ---------- */
    @GetMapping
    public String mostrarCitas(Model model) {
        model.addAttribute("citas", citaService.obtenerTodas());
        return "citas";
    }

    /* ---------- NUEVA ---------- */
    @GetMapping("/nueva")
    public String nueva(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("usuarios",  usuarioService.obtenerTodos());
        model.addAttribute("servicios", servicioService.listar());
        model.addAttribute("modo", "crear");
        return "cita-form";
    }
    /* ---------- EDITAR ---------- */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cita cita = citaService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada id=" + id));
        model.addAttribute("cita", cita);
        model.addAttribute("usuarios",  usuarioService.obtenerTodos());
        model.addAttribute("servicios", servicioService.listar());
        model.addAttribute("modo", "editar");
        return "cita-form";
    }
    /* ---------- GUARDAR ---------- */
    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("cita") Cita cita,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuarios",  usuarioService.obtenerTodos());
            model.addAttribute("servicios", servicioService.listar());
            model.addAttribute("modo", (cita.getId() == null) ? "crear" : "editar");
            return "cita-form";
        }
        citaService.guardar(cita);
        return "redirect:/citas";
    }
    /* ---------- ELIMINAR ---------- */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        citaService.eliminar(id);
        return "redirect:/citas";
    }
}
