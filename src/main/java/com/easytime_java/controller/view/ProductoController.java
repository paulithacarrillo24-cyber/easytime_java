package com.easytime_java.controller.view;

import com.easytime_java.Service.InventarioService;
import com.easytime_java.Service.ProductoService;
import com.easytime_java.model.Producto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping; // Importación necesaria para POST
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;
    private final InventarioService inventarioService;

    public ProductoController(ProductoService service, InventarioService inventarioService) {
        this.service = service;
        this.inventarioService = inventarioService;
    }
      
    // LISTAR
    @GetMapping 
    public String listarProductos(Model model) {
        model.addAttribute("productos", service.listar());
        return "productos";
    }

    // CREAR - Mostrar Formulario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    // CREAR/EDITAR - Guardar
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        service.guardar(producto);
        return "redirect:/productos";
    }

    // EDITAR - Mostrar Formulario
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = service.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    // ELIMINAR
    // ⭐ CAMBIADO: Ahora usa @PostMapping para mayor seguridad.
    @PostMapping("/eliminar/{id}") 
    public String eliminarProducto(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/productos";
    } 
}