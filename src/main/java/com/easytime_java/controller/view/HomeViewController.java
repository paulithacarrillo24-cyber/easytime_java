package com.easytime_java.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Controller
public class HomeViewController {

    /**
     * Mapea las URLs de la página principal privada (Dashboard) para usuarios autenticados.
     * Solo mapea /home y /dashboard. La ruta "/" ahora es gestionada por PublicController.
     */
    @GetMapping({"/home", "/dashboard"}) // ⭐ IMPORTANTE: Se ha eliminado la ruta "/" de aquí
    public String showDashboard(Model model) {
        
        // Obtener el usuario autenticado para fines informativos en el dashboard
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("nombreUsuario", username);
        
        // Retorna el nombre del archivo de la plantilla (el menú de módulos)
        return "home"; // Esto cargará src/main/resources/templates/home.html
    }
}