package com.easytime_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.easytime_java.model.Servicio;
import com.easytime_java.repository.ServicioRepository;

@Controller
public class ServiciosController {

    @Autowired
    private ServicioRepository repo;

    /* * ELIMINADO: @GetMapping("/") 
     * Este mapeo estaba duplicado y causaba el error 500 (Ambiguous Handler). 
     * La ruta raíz (/) ahora es manejada por HomeViewController o la redirección de login.
     */

    @GetMapping("/servicios")
    public String listar(Model model) {
        model.addAttribute("servicios", repo.findAll());
        return "servicios";
    }

    @GetMapping("/servicios/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "form_servicios";
    }

    @PostMapping("/servicios/guardar")
    public String guardar(@ModelAttribute Servicio servicio) {
        repo.save(servicio);
        return "redirect:/servicios?guardado=true";
    }

    @GetMapping("/servicios/editar/{ID_SERVICIO}")
    public String editar(@PathVariable Integer ID_SERVICIO, Model model) {
        // CORRECCIÓN: El tipo de dato del ID se cambió de Long a Integer.
        model.addAttribute("servicio", repo.findById(ID_SERVICIO).orElseThrow());
        return "form_servicios";
    }

    @GetMapping("/servicios/eliminar/{ID_SERVICIO}")
    public String eliminar(@PathVariable Integer ID_SERVICIO) {
        // CORRECCIÓN: El tipo de dato del ID se cambió de Long a Integer.
        repo.deleteById(ID_SERVICIO);
        return "redirect:/servicios";
    }
}