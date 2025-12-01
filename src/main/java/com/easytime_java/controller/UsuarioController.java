package com.easytime_java.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;

@Controller

public class UsuarioController {

    @Autowired
    private UsuarioRepository repo;

    @GetMapping("/")
    public String redireccionRaiz() {
    return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home") // Página Principal, Autorización Usuario Logueado! [ADMIN,USER]
    public String home(Model model, Authentication auth) {
        model.addAttribute("rol", auth.getAuthorities().toString());
        return "home";
    }

    @GetMapping("/usuarios")
    public String listar(Model model) {
        model.addAttribute("usuarios", repo.findAll());
        return "usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "form_usuarios";
    }

    @PostMapping("/usuarios/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        usuario.setCONTRA_USER(new BCryptPasswordEncoder().encode(usuario.getCONTRA_USER()));
        repo.save(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/editar/{ID_USER}")
    public String editar(@PathVariable Long ID_USER, Model model) {
        model.addAttribute("usuario", repo.findById(ID_USER).orElseThrow());
        return "form_usuarios";
    }

    @GetMapping("/usuarios/eliminar/{ID_USER}")
    public String eliminar(@PathVariable Long ID_USER) {
        repo.deleteById(ID_USER);
        return "redirect:/usuarios";
    }

    @GetMapping("/perfil")
    public String perfil(Model model, Authentication auth) {
        String correoUser = auth.getName(); // nombre de usuario autenticado
        Usuario usuario = repo.findByCorreoUser(correoUser).orElseThrow();
        model.addAttribute("usuario", usuario);
        return "form";
    }

    @PostMapping("/perfil/guardar")

    public String guardarPerfil(@ModelAttribute Usuario usuario, Authentication auth) {
        String correoUser = auth.getName();
        Usuario actual = repo.findByCorreoUser(correoUser).orElseThrow();
        actual.setNOM_USER(usuario.getNOM_USER());
        actual.setAPE_USER(usuario.getAPE_USER());
        actual.setTEL_USER(usuario.getTEL_USER());
        actual.setCONTRA_USER(new BCryptPasswordEncoder().encode(usuario.getCONTRA_USER()));
        repo.save(actual);
        return "redirect:/home?actualizado";
    }
}