package com.easytime_java.controller.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    /**
     * Muestra la página de login.
     * Añade un mensaje al modelo si la redirección proviene de un acceso denegado al carrito.
     */
    @GetMapping("/login")
    public String login(
        Model model,
        @RequestParam(value = "action", required = false) String action
    ) {
        // Si el parámetro 'action' es 'carrito', significa que el usuario intentó acceder a /carrito/**
        if ("carrito".equals(action)) {
            model.addAttribute("loginMessage", "Debes iniciar sesión para añadir productos al carrito.");
        }

        // Esto busca el archivo login.html 
        return "login";
    }
}