package com.easytime_java.controller.api;

import com.easytime_java.model.Rol;
import com.easytime_java.repository.RolRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/roles") // Mapea todas las rutas bajo /roles
public class RolController {

    private final RolRepository rolRepo;

    public RolController(RolRepository rolRepo) {
        this.rolRepo = rolRepo;
    }

    /**
     * Muestra la lista de todos los roles.
     * GET /roles
     */
    @GetMapping
    public String listar(Model model) {
        List<Rol> roles = rolRepo.findAll();
        model.addAttribute("roles", roles);
        return "roles"; // Retorna el template roles.html (la tabla de roles)
    }

    /**
     * Muestra el formulario para crear un nuevo rol.
     * GET /roles/nuevo
     */
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("rol", new Rol());
        return "form_roles"; // Retorna el template form_roles.html
    }

    /**
     * Guarda o actualiza un rol (el ID se maneja en el objeto Rol).
     * POST /roles/guardar
     */
    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Rol rol) {
        // En el formulario, si ID_ROL es nulo, es una creación. Si tiene valor, es una edición.
        rolRepo.save(rol);
        return "redirect:/roles"; // Redirige a la lista de roles
    }

    /**
     * Muestra el formulario con los datos del rol para editar.
     * GET /roles/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        Optional<Rol> opt = rolRepo.findById(id);
        if (opt.isPresent()) {
            model.addAttribute("rol", opt.get());
            return "form_roles"; // Reutiliza el mismo formulario de nuevo rol
        }
        return "redirect:/roles";
    }

    /**
     * Elimina un rol por su ID.
     * GET /roles/eliminar/{id}
     */
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        rolRepo.deleteById(id);
        return "redirect:/roles";
    }
}
