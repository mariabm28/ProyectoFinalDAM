package com.tienda.proyectofinal.controller;


import com.tienda.proyectofinal.model.Usuario;
import com.tienda.proyectofinal.repository.UsuarioRepository;
import com.tienda.proyectofinal.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getUsuarios() {
        return usuarioService.obtenerTodos();
    }

    @PostMapping
    public ResponseEntity<Usuario>  crearUsuario(@RequestBody Usuario usuario){
        return ResponseEntity.ok(usuarioService.guardar(usuario));
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminar(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuario) {
        Usuario actualizado = usuarioService.actualizar(id, usuario);
        return ResponseEntity.ok(actualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Usuario>> obtenerUsuarioPorId(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }


}