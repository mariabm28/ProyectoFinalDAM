package com.tienda.proyectofinal.controller;

import com.tienda.proyectofinal.model.Usuario;
import com.tienda.proyectofinal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.OutputStream;
import java.util.List;

@Controller
@RequestMapping("/usuarios")
public class UsuarioWebController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioWebController(UsuarioService service) {
        this.usuarioService = service;
    }
    /** LISTAR */
    @GetMapping
    public String mostrarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioService.obtenerTodos();
        System.out.println(usuarios);
        System.out.println("Lista desde el controlador: " + usuarios.size());
        model.addAttribute("usuarios", usuarios); // Pasa la lista de usuarios al modelo
        return "usuarios"; // Nombre de la plantilla Thymeleaf (usuarios.html)
    }
    /** FORMULARIO CREAR  */
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("modo", "crear");
        return "usuario-form";
    }
    /** FORMULARIO EDITAR  */
    @GetMapping("/editar/{id}")
    public String formularioEditar(@PathVariable Long id, Model model) {
        model.addAttribute("usuario", usuarioService.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Id no encontrado")));
        model.addAttribute("modo", "editar");
        return "usuario-form";
    }
    /** GUARDAR (crear o actualizar) */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute @Valid Usuario usuario,
                          BindingResult br,
                          RedirectAttributes ra) {

        if (br.hasErrors()) {
            return "usuario-form";
        }
        usuarioService.guardar(usuario);
        ra.addFlashAttribute("ok", "Usuario guardado");
        return "redirect:/usuarios";
    }
    /** ELIMINAR */
    @PostMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {
        usuarioService.eliminar(id);
        ra.addFlashAttribute("ok", "Usuario eliminado");
        return "redirect:/usuarios";
    }
}