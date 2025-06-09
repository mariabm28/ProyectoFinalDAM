package com.tienda.proyectofinal.service;

import org.json.JSONObject;
//import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class FirebaseAuthRestService {

    private final String API_KEY = "AIzaSyBtR0JjELDzNquW9EjbdUIxO8FU6RyoXpA"; //  AQUI VA TU API KEY
    public boolean authenticate(String email, String password) {
        String url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=" + API_KEY;

        Map<String, Object> request = Map.of(
                "email", email,
                "password", password,
                "returnSecureToken", true
        );

        try {
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.postForObject(url, request, String.class);
            JSONObject json = new JSONObject(response);

            return json.has("idToken"); // login correcto
        } catch (Exception e) {
            return false; // login incorrecto
        }
    }
}
