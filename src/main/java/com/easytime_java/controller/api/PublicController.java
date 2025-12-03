package com.easytime_java.controller.api;

import com.easytime_java.model.Producto; // <<<< AJUSTA ESTA RUTA <<<<
import com.easytime_java.repository.ProductoRepository; // <<<< AJUSTA ESTA RUTA <<<<
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class PublicController {

    // INYECCIÓN: Declaración del Repositorio de Productos
    private final ProductoRepository productoRepository;

    // CONSTRUCTOR: Inyección de dependencias
    public PublicController(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // Maneja la ruta principal (/) y /index (Productos)
    @GetMapping({"/", "/index"}) 
    public String showProducts(Model model) {
        
        // 1. OBTENER DATOS: Consulta la base de datos para obtener todos los productos
        List<Producto> productos = productoRepository.findAll();
        
        // 2. ENVIAR DATOS: Envía la lista de productos a la vista bajo el nombre 'productos'
        model.addAttribute("productos", productos); 
        
        // 3. ESTADO ACTIVO: Establece la variable para indicar que la sección 'home' está activa
        model.addAttribute("currentPage", "home"); 
        
        return "index";
    }

    // Maneja la ruta /servicios/publico
    @GetMapping("/servicios/publico")
    public String showPublicServices(Model model) {
        // En este método solo se establece el estado activo de la página,
        // asumiendo que la lógica para Servicios se implementará más tarde.
        model.addAttribute("currentPage", "servicios");
        return "index"; 
    }
}