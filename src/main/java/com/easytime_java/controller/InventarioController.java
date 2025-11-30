package com.easytime_java.controller;

import com.easytime_java.model.Inventario;
import com.easytime_java.Service.InventarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/inventarios")
public class InventarioController {

    private final InventarioService service;

    public InventarioController(InventarioService service) {
        this.service = service;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("inventarios", service.listar());
        return "inventarios";
    }

    @GetMapping("/crear")
    public String mostrarCrear(Model model) {
        model.addAttribute("inventario", new Inventario());
        return "inventario-form";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute Inventario inv) {
        service.guardar(inv);
        return "redirect:/inventarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("inventario", service.obtenerPorId(id));
        return "inventario-form";
    }

    @PostMapping("/{id}/editar")
    public String guardarEditar(@PathVariable Integer id, @ModelAttribute Inventario inv) {
        inv.setIdInventario(id);
        service.guardar(inv);
        return "redirect:/inventarios";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/inventarios";
    }
}

