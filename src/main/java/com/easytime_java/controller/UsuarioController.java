package com.easytime_java.controller;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = repo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // template usuarios.html
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "form_usuarios"; // template form_usuarios.html
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        // Asignar el ID_ROL_USER según el rol seleccionado en el formulario
        if ("Cliente".equalsIgnoreCase(usuario.getRol())) {
            usuario.setIdRolUser(1);
        } else if ("Administrador".equalsIgnoreCase(usuario.getRol())) {
            usuario.setIdRolUser(2);
        } else if ("Jefe de patio".equalsIgnoreCase(usuario.getRol())) {
            usuario.setIdRolUser(3);
        } else {
            // Valor por defecto si no coincide con ninguno
            usuario.setIdRolUser(1);
        }

        repo.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        Optional<Usuario> opt = repo.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("usuario", opt.get());
            return "form_usuarios";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        repo.deleteById(id);
        return "redirect:/usuarios";
    }
}
