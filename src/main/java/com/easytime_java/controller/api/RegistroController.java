package com.easytime_java.controller.api;

import com.easytime_java.model.Usuario;
import com.easytime_java.model.Rol; // Necesitas importar la entidad Rol
import com.easytime_java.repository.UsuarioRepository;
import com.easytime_java.repository.RolRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Optional; 

@Controller
public class RegistroController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final RolRepository rolRepository; 

    public RegistroController(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder, RolRepository rolRepository) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.rolRepository = rolRepository;
    }

    // Método GET para mostrar el formulario (Ruta: /register)
    @GetMapping("/register") 
    public String showRegistrationForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("roles", rolRepository.findAll()); 
        return "registro"; // Nombre de la plantilla: registro.html
    }

    // ⭐ CORRECCIÓN CLAVE: El POST vuelve a usar la ruta /register
    // Esto resuelve el error de "Ambiguous mapping" con UsuarioController
    @PostMapping("/register") 
    public String registerUser(@ModelAttribute Usuario usuario) {
        
        // 1. Cifrar la contraseña
        String encodedPassword = passwordEncoder.encode(usuario.getPassword());
        usuario.setPassword(encodedPassword);
        
        // 2. Manejar la relación Rol (Usando el ID auxiliar que viene del formulario)
        Integer idRol = usuario.getIdRolUser();
        
        if (idRol == null || idRol <= 0) {
             // Este caso solo ocurriría si el campo 'required' falla o no hay roles
             return "redirect:/register?error=rol_missing";
        }
        
        // Buscar el objeto Rol completo por el ID
        Optional<Rol> rolOptional = rolRepository.findById(idRol);
        
        if (rolOptional.isPresent()) {
            usuario.setRol(rolOptional.get()); // Asigna el objeto Rol
        } else {
            // Manejar error si el Rol no existe
            System.err.println("❌ ERROR: El Rol con ID " + idRol + " no existe.");
            return "redirect:/register?error=rol_invalid";
        }

        // 3. Guardar en la base de datos
        try {
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            System.err.println("❌ ERROR: No se pudo guardar el usuario en la base de datos.");
            e.printStackTrace();
            return "redirect:/register?error=db_failure"; 
        }

        return "redirect:/login?success=true"; 
    }
}