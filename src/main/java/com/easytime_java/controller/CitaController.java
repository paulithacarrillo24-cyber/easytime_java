package com.easytime_java.controller;

import com.easytime_java.model.Cita;
import com.easytime_java.model.Usuario;
import com.easytime_java.repository.CitaRepository;
import com.easytime_java.repository.ServicioRepository;
import com.easytime_java.repository.UsuarioRepository;
import org.springframework.security.access.prepost.PreAuthorize; // Importar para proteger la ruta
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
// Se elimina el import de Collectors ya que el filtro en memoria ya no se necesita

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;

    public CitaController(CitaRepository citaRepository, UsuarioRepository usuarioRepository, ServicioRepository servicioRepository) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
    }

    // --- MÉTODOS DE FORMULARIO (Se mantienen igual) ---

    @GetMapping("/nueva")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("cita", new Cita());
        model.addAttribute("servicios", servicioRepository.findAll());
        return "form_cita";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Integer id, Model model) {
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de Cita inválido:" + id));

        model.addAttribute("cita", cita);
        model.addAttribute("servicios", servicioRepository.findAll());

        return "form_cita";
    }

    // --- LÓGICA DE LISTADO DE CITAS ACTIVAS (FUTURAS) ---

    @GetMapping("/lista")
    public String listarCitas(Model model, Authentication auth) {
        String username = auth.getName();
        List<Cita> citas;
        LocalDateTime now = LocalDateTime.now();

        // 1. Verificar si el usuario es Administrador (para habilitar la vista de Historial)
        boolean esAdmin = auth.getAuthorities().stream()
                              .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        model.addAttribute("esAdmin", esAdmin); // Pasar el rol a la vista
        model.addAttribute("titulo", "Citas Próximas (Activas)"); // Título para la vista

        if (esAdmin) {
            // Lógica para el Administrador: Mostrar TODAS las citas FUTURAS
            citas = citaRepository.findByFechaCitaAfterOrderByFechaCitaAsc(now);
            System.out.println("DEBUG: Mostrando TODAS las citas FUTURAS (ADMIN). Total: " + citas.size());
        } else {
            // Lógica para Cliente y Jefe de Patio: Mostrar solo sus citas FUTURAS
            Usuario usuario = usuarioRepository.findByCorreoUser(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

            // ⭐ CORRECCIÓN DE EFICIENCIA: Uso del método combinado en el repositorio
            citas = citaRepository.findByUsuarioIdUsuarioAndFechaCitaAfterOrderByFechaCitaAsc(usuario.getIdUser(), now);

            System.out.println("DEBUG: Mostrando citas personales FUTURAS. Total: " + citas.size());
        }

        model.addAttribute("citas", citas);
        return "Citas";
    }

    // --- NUEVA LÓGICA PARA HISTORIAL (SOLO ADMIN) ---

    @GetMapping("/historial")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')") // Proteger la ruta solo para el Administrador
    public String listarHistorialCitas(Model model) {

        LocalDateTime now = LocalDateTime.now();

        // Obtener TODAS las citas con fecha PASADA (Historial)
        List<Cita> citas = citaRepository.findByFechaCitaBeforeOrderByFechaCitaDesc(now);

        model.addAttribute("citas", citas);
        model.addAttribute("esAdmin", true);
        model.addAttribute("titulo", "Historial de Citas (Pasadas)");
        return "Citas"; // Reutilizamos la misma plantilla Citas.html
    }

    // --- OTROS MÉTODOS (Se mantienen igual) ---

    @GetMapping
    public String redireccionarALista() {
        return "redirect:/citas/lista";
    }

    @GetMapping("/{id}")
    public String obtenerPorId(@PathVariable Integer id, Model model) {
        Cita cita = citaRepository.findById(id).orElse(null);
        model.addAttribute("cita", cita);
        return "detalle_cita";
    }

    @PostMapping
    public String crearCita(@ModelAttribute Cita cita, Authentication auth) {

        String username = auth.getName();
        Usuario usuario = usuarioRepository.findByCorreoUser(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        cita.setUsuarioIdUsuario(usuario.getIdUser());

        if (cita.getCreatedAt() == null) {
            cita.setCreatedAt(LocalDateTime.now());
        }

        citaRepository.save(cita);

        return "redirect:/citas/lista";
    }

    @PostMapping("/editar/{id}")
    public String actualizar(@PathVariable Integer id, @ModelAttribute Cita cita) {

        cita.setIdCita(id);

        cita.setUpdateAt(LocalDateTime.now());

        citaRepository.save(cita);

        return "redirect:/citas/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Integer id) {

        citaRepository.deleteById(id);

        return "redirect:/citas/lista";
    }
}