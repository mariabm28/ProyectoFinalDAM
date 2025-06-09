package com.tienda.proyectofinal.service;

import com.tienda.proyectofinal.model.Usuario;
import com.tienda.proyectofinal.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        System.out.println("Usuarios encontrados: " + usuarios.size());
        return usuarios;
    }
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    public Usuario guardar(Usuario usuario) {
        if (usuario.getId() != null) {
            // Usuario existente, buscar en la base de datos
            Usuario existente = usuarioRepository.findById(usuario.getId()).orElse(null);
            if (existente != null && (usuario.getPassword() == null || usuario.getPassword().isEmpty())) {
                usuario.setPassword(existente.getPassword());
            }
        }
        return usuarioRepository.save(usuario);

    }

    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    public Usuario actualizar(Long id, Usuario usuario) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);
        if (usuarioExistente.isPresent()) {
            Usuario u = usuarioExistente.get();
            u.setNombre(usuario.getNombre());
            u.setEmail(usuario.getEmail());
            u.setPassword(usuario.getPassword());
            return usuarioRepository.save(u);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado");
        }
    }

}
