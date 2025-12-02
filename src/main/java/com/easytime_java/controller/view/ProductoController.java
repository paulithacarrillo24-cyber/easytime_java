package com.easytime_java.controller.view;

import com.easytime_java.Service.InventarioService;
import com.easytime_java.Service.ProductoService;
import com.easytime_java.model.Producto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; // Importación necesaria

@Controller
@RequestMapping("/productos") // <--- ESTO ARREGLA EL 404 DEL DASHBOARD
public class ProductoController { // Renombrado a ProductoController por convención

    private final ProductoService service;
    private final InventarioService inventarioService;

    public ProductoController(ProductoService service, InventarioService inventarioService) {
        this.service = service;
        this.inventarioService = inventarioService;
    }
      
    // LISTAR
    // Mapea a /productos (resuelve el error 404 del Dashboard)
    @GetMapping 
    public String listarProductos(Model model) {
        model.addAttribute("productos", service.listar());
        return "productos"; // Asume la plantilla 'productos.html'
    }

    // CREAR - Mostrar Formulario
    // Mapea a /productos/nuevo
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos"; // Asume la plantilla 'form_productos.html'
    }

    // CREAR - Guardar
    // Mapea a POST /productos/guardar
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        service.guardar(producto);
        return "redirect:/productos"; // Redirige a la ruta base /productos
    }

    // EDITAR - Mostrar Formulario
    // Mapea a /productos/editar/{id}
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = service.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    // ELIMINAR
    // Mapea a /productos/eliminar/{id}
    // Se recomienda cambiar a un PostMapping para eliminación real.
    @GetMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/productos"; // Redirige a la ruta base /productos
    }      
}