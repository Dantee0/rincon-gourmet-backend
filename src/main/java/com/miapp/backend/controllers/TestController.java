package com.miapp.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/saludo")
    public String saludar() {
        return "¡Hola! El backend de Rincóngourmet está funcionando perfecto.";
    }
}