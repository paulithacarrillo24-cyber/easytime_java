package com.easytime_java.controller.api;

import com.easytime_java.model.Inventario;
// ⭐ Importar el modelo Proveedor (Asegúrate que esta ruta sea correcta)
import com.easytime_java.model.Proveedor;
import com.easytime_java.Service.InventarioService;
// ⭐ Importar el servicio ProveedorService (Asegúrate que esta ruta sea correcta)
import com.easytime_java.Service.ProveedorService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List; // ⭐ Importar List

@Controller
@RequestMapping("/inventarios")
public class InventarioController {

    private final InventarioService inventarioService; // Renombrado a inventarioService
    // ⭐ NUEVO: Servicio para Proveedores
    private final ProveedorService proveedorService;

    // ⭐ MODIFICACIÓN en el constructor para inyectar ambos servicios
    public InventarioController(InventarioService inventarioService, ProveedorService proveedorService) {
        this.inventarioService = inventarioService;
        this.proveedorService = proveedorService;
    }

    // Mapea /inventarios. Muestra la lista de ítems del inventario.
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("inventarios", inventarioService.listar());
        return "inventarios";
    }

    // ⭐ MODIFICACIÓN CLAVE: Cargar la lista de proveedores al modelo
    @GetMapping("/crear")
    public String mostrarCrear(Model model) {
        model.addAttribute("inventario", new Inventario());

        // 🌟 OBTENER PROVEEDORES: Asume que proveedorService tiene un método 'listar' o
        // 'findAll'
        List<Proveedor> proveedores = proveedorService.listar();
        model.addAttribute("listaProveedores", proveedores);

        return "form_inventario";
    }

    // Mapea POST /inventarios/crear. Guarda el nuevo ítem.
    @PostMapping("/crear")
    public String crear(@ModelAttribute("inventario") Inventario inv) {

        inv.setUpdateAt(LocalDateTime.now());

        inventarioService.guardar(inv);
        return "redirect:/inventarios";
    }

    // ⭐ MODIFICACIÓN CLAVE: También cargar la lista de proveedores para la edición
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("inventario", inventarioService.obtenerPorId(id));

        // 🌟 OBTENER PROVEEDORES: Necesario para que el desplegable se llene en el
        // formulario de edición
        List<Proveedor> proveedores = proveedorService.listar();
        model.addAttribute("listaProveedores", proveedores);

        return "form_inventario";
    }

    // Mapea POST /inventarios/{id}/editar. Guarda los cambios.
    @PostMapping("/{id}/editar")
    public String guardarEditar(@PathVariable Integer id, @ModelAttribute Inventario inv) {
        inv.setIdInventario(id);

        inv.setUpdateAt(LocalDateTime.now());

        inventarioService.guardar(inv);
        return "redirect:/inventarios";
    }

    // Mapea POST /inventarios/{id}/eliminar. Elimina el ítem.
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        inventarioService.eliminar(id);
        return "redirect:/inventarios";
    }
}