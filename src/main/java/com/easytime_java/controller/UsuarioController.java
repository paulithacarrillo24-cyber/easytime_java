package com.easytime_java.controller;

import com.easytime_java.model.Usuario;
import com.easytime_java.model.Rol; 
import com.easytime_java.repository.UsuarioRepository;
import com.easytime_java.repository.RolRepository;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo; 
    private final RolRepository rolRepo; 
    private final PasswordEncoder passwordEncoder; // ⭐ 1. DECLARAR PasswordEncoder

    // ⭐ 2. INYECTAR PasswordEncoder
    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = usuarioRepo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        
        // Cargar roles y pasarlos al formulario
        List<Rol> roles = rolRepo.findAll(); 
        model.addAttribute("roles", roles);
        
        return "form_usuarios"; 
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        
        // ⭐ 3. LÓGICA DE GESTIÓN DE CONTRASEÑA Y ROL
        
        // Si el usuario es nuevo (idUser es null) o se proporcionó una nueva contraseña:
        if (usuario.getIdUser() == null || (usuario.getPassword() != null && !usuario.getPassword().isEmpty())) {
            // Cifrar la nueva contraseña
            String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordCifrada);
        } else if (usuario.getIdUser() != null) {
            // Si el campo de contraseña está vacío al editar un usuario existente,
            // cargamos la contraseña cifrada existente para no borrarla/dejarla en blanco.
            Optional<Usuario> existingUser = usuarioRepo.findById(usuario.getIdUser());
            existingUser.ifPresent(u -> usuario.setPassword(u.getPassword()));
        }
        
        // Lógica del Rol por defecto (se mantiene)
        if (usuario.getIdRolUser() == null || usuario.getIdRolUser() == 0) {
            // Si no se asignó rol en el formulario, asigna el rol por defecto (Cliente)
            usuario.setIdRolUser(1);
        }
        
        usuarioRepo.save(usuario);
        return "redirect:/usuarios";
    }
    
    @GetMapping("/editar/{id}")
    // ⭐ 4. CORRECCIÓN ID: Cambiado Long a Integer
    public String editar(@PathVariable("id") Integer id, Model model) { 
        // ⭐ 5. CORRECCIÓN ID: usuarioRepo.findById espera Integer
        Optional<Usuario> opt = usuarioRepo.findById(id); 
        
        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            
            // Opcional: Limpiar la contraseña antes de enviarla a la vista 
            // por seguridad, para que el campo de formulario aparezca vacío.
            usuario.setPassword(null); 
            
            model.addAttribute("usuario", usuario);
            
            // Cargar roles también en la vista de edición
            List<Rol> roles = rolRepo.findAll(); 
            model.addAttribute("roles", roles);
            
            return "form_usuarios";
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    // ⭐ 6. CORRECCIÓN ID: Cambiado Long a Integer
    public String eliminar(@PathVariable("id") Integer id) { 
        usuarioRepo.deleteById(id); 
        return "redirect:/usuarios";
    }
}