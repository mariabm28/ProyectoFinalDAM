package com.tienda.proyectofinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PanelController {

    @GetMapping("/panel")
    public String panel() {
        return "panel";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}