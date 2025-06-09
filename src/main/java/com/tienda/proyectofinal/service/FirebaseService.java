package com.tienda.proyectofinal.service;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseToken;
import com.google.firebase.auth.UserRecord;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    public FirebaseToken verificarToken(String idToken) {
        try {
            return FirebaseAuth.getInstance().verifyIdToken(idToken);
        } catch (Exception e) {
            throw new RuntimeException("Token inválido o expirado");
        }
    }


}