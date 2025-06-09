package com.tienda.proyectofinal.controller;


import com.google.firebase.auth.FirebaseToken;
import com.tienda.proyectofinal.model.Usuario;
import com.tienda.proyectofinal.repository.UsuarioRepository;
import com.tienda.proyectofinal.service.FirebaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    private final FirebaseService firebaseService;

    public AuthController(FirebaseService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/verify")
    public String verifyToken(@RequestHeader("Authorization") String authorizationHeader) {
        String idToken = authorizationHeader.replace("Bearer ", "");
        FirebaseToken decodedToken = firebaseService.verificarToken(idToken);
        return "Usuario verificado: " + decodedToken.getUid();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario loginRequest) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(loginRequest.getEmail());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            System.out.println("Petición recibida: " + usuario.getEmail());
            if (usuario.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok(usuario);
            }
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email o contraseña incorrectos");
    }


}
