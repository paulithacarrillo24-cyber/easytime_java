package com.easytime_java.controller.api;

import com.easytime_java.model.Usuario;
import com.easytime_java.model.Rol; 
import com.easytime_java.repository.UsuarioRepository;
import com.easytime_java.repository.RolRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort; // Importación necesaria para ordenar

import jakarta.servlet.http.HttpServletResponse; // Importación necesaria para exportación

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo; 
    private final RolRepository rolRepo; 
    private final PasswordEncoder passwordEncoder;
    
    // NOTA: Aquí se deben inyectar los servicios de exportación (ej. pdfService, excelService)
    // una vez que los crees. Por ahora, usamos el repositorio directamente.

    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ⭐ MÉTODO LISTAR ACTUALIZADO CON FILTRADO
    @GetMapping
    public String listar(Model model, 
                         @RequestParam(value = "filtroEstado", required = false) String filtroEstado) {
        
        List<Usuario> usuarios;

        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            // NOTA: Recuerda que debes definir el método 'findByEstUser' en tu UsuarioRepository
            usuarios = usuarioRepo.findByEstUser(estado, Sort.by("idUser").ascending()); 
            model.addAttribute("filtroEstadoActual", filtroEstado);
        } else {
            // Si no hay filtro, lista todos, ordenados por ID
            usuarios = usuarioRepo.findAll(Sort.by("idUser").ascending());
            model.addAttribute("filtroEstadoActual", "");
        }
        
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    // ⭐ RUTA DE CREACIÓN: Usa la plantilla 'registro.html'
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        
        List<Rol> roles = rolRepo.findAll(); 
        model.addAttribute("roles", roles);
        
        // Retorna la vista de registro
        return "registro"; 
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        
        // --- 1. GESTIÓN DE CONTRASEÑA ---
        if (usuario.getIdUser() == null || (usuario.getPassword() != null && !usuario.getPassword().isEmpty())) {
            // Cifrar la nueva contraseña
            String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordCifrada);
        } else if (usuario.getIdUser() != null) {
            // Si el campo de contraseña está vacío al editar un usuario existente,
            // cargamos la contraseña cifrada existente.
            usuarioRepo.findById(usuario.getIdUser())
                       .ifPresent(u -> usuario.setPassword(u.getPassword()));
        }
        
        // --- 2. GESTIÓN DE ROL ---
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == null || usuario.getRol().getIdRol() == 0) {
            // Buscamos el rol por defecto (ID=1) y lo asignamos si existe
            Optional<Rol> defaultRol = rolRepo.findById(1);
            defaultRol.ifPresent(usuario::setRol);
        } else {
            // Si se seleccionó un Rol en el formulario, debemos buscar el objeto completo
            // para que la relación ManyToOne se guarde correctamente en la DB.
            rolRepo.findById(usuario.getRol().getIdRol())
                   .ifPresent(usuario::setRol);
        }
        
        usuarioRepo.save(usuario);
        return "redirect:/usuarios";
    }
    
    // RUTA DE EDICIÓN
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) { 
        Optional<Usuario> opt = usuarioRepo.findById(id); 
        
        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            
            // Limpiar la contraseña antes de enviarla a la vista 
            usuario.setPassword(null); 
            
            model.addAttribute("usuario", usuario);
            
            // Cargar roles para el select en la vista de edición
            List<Rol> roles = rolRepo.findAll(); 
            model.addAttribute("roles", roles);
            
            // Retorna la VISTA DE EDICIÓN DEDICADA
            return "form_usuarios"; 
        }
        return "redirect:/usuarios";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) { 
        usuarioRepo.deleteById(id); 
        return "redirect:/usuarios";
    }
    
    // ⭐ RUTA DE EXPORTACIÓN A EXCEL
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response, 
                              @RequestParam(value = "filtroEstado", required = false) String filtroEstado) throws Exception {
        
        // 1. Configurar la respuesta HTTP para la descarga
        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_easytime_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        // 2. Aplicar el mismo filtro que en la vista
        List<Usuario> usuarios;
        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            usuarios = usuarioRepo.findByEstUser(estado, Sort.by("idUser").ascending()); 
        } else {
            usuarios = usuarioRepo.findAll(Sort.by("idUser").ascending());
        }

        // 3. Generar el Excel (DEBES CREAR LA CLASE ExcelGenerator E INYECTAR EL SERVICIO)
        // Ejemplo teórico: excelService.export(usuarios, response.getOutputStream());
        
        // Simulación temporal:
        response.getWriter().write("Simulación de contenido Excel para " + usuarios.size() + " usuarios. [PENDIENTE IMPLEMENTACIÓN DEL GENERADOR]");
    }

    // ⭐ RUTA DE EXPORTACIÓN A PDF
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response,
                            @RequestParam(value = "filtroEstado", required = false) String filtroEstado) throws Exception {
        
        // 1. Configurar la respuesta HTTP para la descarga
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_easytime_" + System.currentTimeMillis() + ".pdf";
        response.setHeader(headerKey, headerValue);

        // 2. Aplicar el mismo filtro que en la vista
        List<Usuario> usuarios;
        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            usuarios = usuarioRepo.findByEstUser(estado, Sort.by("idUser").ascending()); 
        } else {
            usuarios = usuarioRepo.findAll(Sort.by("idUser").ascending());
        }

        // 3. Generar el PDF (DEBES CREAR LA CLASE PdfGenerator E INYECTAR EL SERVICIO)
        // Ejemplo teórico: pdfService.export(usuarios, response.getOutputStream());
        
        // Simulación temporal:
        response.getWriter().write("Simulación de contenido PDF para " + usuarios.size() + " usuarios. [PENDIENTE IMPLEMENTACIÓN DEL GENERADOR]");
    }
}