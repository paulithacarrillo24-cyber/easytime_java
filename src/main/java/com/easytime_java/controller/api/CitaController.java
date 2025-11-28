package com.easytime_java.controller.api;

import com.easytime_java.model.Cita;
import com.easytime_java.model.Usuario;
import com.easytime_java.repository.CitaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaRepository citaRepository;

    public CitaController(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // Crear cita y asociarla al usuario logueado
    @PostMapping
    public ResponseEntity<?> crearCita(@RequestBody Cita cita, Authentication authentication) {

        // Obtener usuario autenticado
        Usuario usuario = (Usuario) authentication.getPrincipal();
        cita.setUsuarioIdUsuario(usuario.getIdUsuario());

        // VALIDAR que la fecha/hora no esté ocupada
        if (citaRepository.existsByFechaCita(cita.getFechaCita())) {
            return ResponseEntity.badRequest().body("❌ Ese horario ya está ocupado");
        }

        // Registrar fecha de creación
        cita.setCreatedAt(LocalDateTime.now());

        return ResponseEntity.ok(citaRepository.save(cita));
    }

    @GetMapping
    public List<Cita> listarCitas() {
        return citaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Cita obtenerPorId(@PathVariable Integer id) {
        return citaRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Cita actualizar(@PathVariable Integer id, @RequestBody Cita cita) {
        cita.setIdCita(id);
        cita.setUpdateAt(LocalDateTime.now());
        return citaRepository.save(cita);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Integer id) {
        citaRepository.deleteById(id);
    }
}
