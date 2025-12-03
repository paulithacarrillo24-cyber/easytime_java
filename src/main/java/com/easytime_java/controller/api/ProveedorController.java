package com.easytime_java.controller.api;

import com.easytime_java.model.Proveedor;
import com.easytime_java.Service.ProveedorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/proveedores")
public class ProveedorController {

    private final ProveedorService service;

    public ProveedorController(ProveedorService service) {
        this.service = service;
    }

    // 1. Mostrar lista (GET /proveedores)
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("proveedores", service.listar());
        return "proveedores"; // Busca la plantilla 'proveedores.html'
    }

    // 2. Mostrar formulario de creación (GET /proveedores/crear)
    @GetMapping("/crear")
    public String mostrarCrear(Model model) {
        model.addAttribute("proveedor", new Proveedor());
        return "form_proveedor"; // Busca la plantilla 'form_proveedor.html'
    }

    // 3. Guardar nuevo proveedor (POST /proveedores/crear)
    @PostMapping("/crear")
    public String crear(@ModelAttribute("proveedor") Proveedor prov) {
        // ⭐ Corregir NOT NULL: Asignar fechas antes de guardar
        prov.setCreatedAt(LocalDateTime.now());
        prov.setUpdateAt(LocalDateTime.now());
        prov.setEstProv(1); // Asignar estado por defecto (ej: activo)

        service.guardar(prov);
        return "redirect:/proveedores";
    }

    // 4. Mostrar formulario de edición (GET /proveedores/{id}/editar)
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        Proveedor prov = service.obtenerPorId(id)
            .orElseThrow(() -> new IllegalArgumentException("ID de proveedor inválido:" + id));
            
        model.addAttribute("proveedor", prov);
        return "form_proveedor"; // Usa la misma plantilla para crear/editar
    }

    // 5. Guardar cambios (POST /proveedores/{id}/editar)
    @PostMapping("/{id}/editar")
    public String guardarEditar(@PathVariable Integer id, @ModelAttribute Proveedor prov) {
        prov.setIdProveedor(id);
        
        // ⭐ Corregir NOT NULL: Mantener la fecha de creación y actualizar la de modificación
        prov.setCreatedAt(service.obtenerPorId(id).get().getCreatedAt());
        prov.setUpdateAt(LocalDateTime.now());
        
        service.guardar(prov);
        return "redirect:/proveedores";
    }

    // 6. Eliminar proveedor (POST /proveedores/{id}/eliminar)
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/proveedores";
    }
}
