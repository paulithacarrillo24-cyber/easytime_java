package com.easytime_java.controller.api;

import com.easytime_java.model.Inventario;
import com.easytime_java.Service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventario") // Mapeado a singular para coincidir con el enlace del Dashboard (/inventario)
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    // Mapea /inventario. Muestra la lista de ítems del inventario.
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("inventarios", service.listar());
        return "inventarios"; // Asume la plantilla 'inventarios.html'
    }

    // Mapea /inventario/crear. Muestra el formulario para crear un nuevo ítem.
    @GetMapping("/crear")
    public String mostrarCrear(Model model) {
        model.addAttribute("inventario", new Inventario());
        return "inventario-form"; // Asume la plantilla 'inventario-form.html'
    }

    // Mapea POST /inventario/crear. Guarda el nuevo ítem.
    @PostMapping("/crear")
    public String crear(@ModelAttribute Inventario inv) {
        service.guardar(inv);
        return "redirect:/inventario"; // Redirige a la lista
    }

    // Mapea /inventario/{id}/editar. Muestra el formulario de edición.
    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("inventario", service.obtenerPorId(id));
        return "inventario-form";
    }

    // Mapea POST /inventario/{id}/editar. Guarda los cambios.
    @PostMapping("/{id}/editar")
    public String guardarEditar(@PathVariable Integer id, @ModelAttribute Inventario inv) {
        inv.setIdInventario(id);
        service.guardar(inv);
        return "redirect:/inventario";
    }

    // Mapea POST /inventario/{id}/eliminar. Elimina el ítem.
    // Usamos POST aquí, ya que la eliminación (cambio de estado) es una acción no idempotente.
    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/inventario";
    }
}