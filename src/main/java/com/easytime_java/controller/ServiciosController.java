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

    @GetMapping("/")
    public String redireccionRaiz() {
        return "redirect:/servicios";
    }

    /*@GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home(Model model, Authentication auth) {
        model.addAttribute("rol", auth.getAuthorities().toString());
        return "home";
    }*/

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
    public String editar(@PathVariable Long ID_SERVICIO, Model model) {
        model.addAttribute("servicio", repo.findById(ID_SERVICIO).orElseThrow());
        return "form_servicios";
    }

    @GetMapping("/servicios/eliminar/{ID_SERVICIO}")
    public String eliminar(@PathVariable Long ID_SERVICIO) {
        repo.deleteById(ID_SERVICIO);
        return "redirect:/servicios";
    }
}
