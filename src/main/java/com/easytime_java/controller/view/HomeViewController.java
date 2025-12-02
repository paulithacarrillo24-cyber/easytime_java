package com.easytime_java.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;

@Controller
public class HomeViewController {

    /**
     * Mapea todas las URLs de la página principal:
     * 1. / (Ruta raíz)
     * 2. /home (Ruta de acceso directo)
     * 3. /dashboard (Ruta de redirección post-login de SecurityConfig)
     * La ruta "/" ahora es única en la aplicación y apunta al menú principal.
     */
    @GetMapping({"/", "/home", "/dashboard"})
    public String showDashboard(Model model) {
        
        // --- Opcional: Mostrar el nombre de usuario (Descomentado y corregido) ---
        // Obtener el usuario autenticado para fines informativos en el dashboard
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        model.addAttribute("nombreUsuario", username);
        
        // Retorna el nombre del archivo de la plantilla
        return "home"; // Esto cargará src/main/resources/templates/home.html
    }
}