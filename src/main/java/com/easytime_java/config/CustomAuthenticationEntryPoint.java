package com.easytime_java.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;

/**
 * Punto de entrada personalizado para manejar accesos no autorizados.
 * Si el acceso es a una ruta protegida del carrito, redirige al login con 
 * un parámetro específico para mostrar un mensaje al usuario.
 */
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {

        // Obtener la URI solicitada
        String requestedUri = request.getRequestURI();

        // Verificar si la URL solicitada contiene "/carrito/"
        if (requestedUri.contains("/carrito/")) {
            // Redirige a /login con el parámetro 'action=carrito'
            response.sendRedirect(request.getContextPath() + "/login?action=carrito");
        } else {
            // Para cualquier otra ruta protegida, redirige al login estándar
            response.sendRedirect(request.getContextPath() + "/login");
        }
    }
}