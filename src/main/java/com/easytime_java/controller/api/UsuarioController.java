package com.easytime_java.controller.api;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easytime_java.model.Rol;
import com.easytime_java.model.Usuario;
import com.easytime_java.repository.RolRepository;
import com.easytime_java.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository usuarioRepo;
    private final RolRepository rolRepo;
    private final PasswordEncoder passwordEncoder;

    public UsuarioController(UsuarioRepository usuarioRepo, RolRepository rolRepo, PasswordEncoder passwordEncoder) {
        this.usuarioRepo = usuarioRepo;
        this.rolRepo = rolRepo;
        this.passwordEncoder = passwordEncoder;
    }

    // ===== LISTAR CON PAGINACIÓN Y FILTRO =====
    @GetMapping
    public String listar(Model model,
                         @RequestParam(value = "page", defaultValue = "0") int page,
                         @RequestParam(value = "q", required = false) String q,
                         @RequestParam(value = "filtroEstado", required = false) String filtroEstado) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("idUser").ascending()); // 10 registros por página
        Page<Usuario> usuariosPage;

        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            usuariosPage = usuarioRepo.findByEstUser(estado, pageable);
            model.addAttribute("filtroEstadoActual", filtroEstado);
        } else if (q != null && !q.isEmpty()) {
            usuariosPage = usuarioRepo.findByNomUserContainingIgnoreCaseOrCorreoUserContainingIgnoreCase(q, q, pageable);
            model.addAttribute("q", q);
        } else {
            usuariosPage = usuarioRepo.findAll(pageable);
            model.addAttribute("filtroEstadoActual", "");
        }

        model.addAttribute("usuarios", usuariosPage.getContent()); // solo 10 registros
        model.addAttribute("page", page);
        model.addAttribute("totalPages", usuariosPage.getTotalPages());

        return "usuarios";
    }

    // ===== CREAR NUEVO USUARIO =====
    @GetMapping("/nuevo")
    public String formularioNuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        List<Rol> roles = rolRepo.findAll();
        model.addAttribute("roles", roles);
        return "registro";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Usuario usuario) {
        // --- 1. Contraseña ---
        if (usuario.getIdUser() == null || (usuario.getPassword() != null && !usuario.getPassword().isEmpty())) {
            String passwordCifrada = passwordEncoder.encode(usuario.getPassword());
            usuario.setPassword(passwordCifrada);
        } else if (usuario.getIdUser() != null) {
            usuarioRepo.findById(usuario.getIdUser())
                       .ifPresent(u -> usuario.setPassword(u.getPassword()));
        }

        // --- 2. Rol ---
        if (usuario.getRol() == null || usuario.getRol().getIdRol() == null || usuario.getRol().getIdRol() == 0) {
            Optional<Rol> defaultRol = rolRepo.findById(1);
            defaultRol.ifPresent(usuario::setRol);
        } else {
            rolRepo.findById(usuario.getRol().getIdRol())
                   .ifPresent(usuario::setRol);
        }

        usuarioRepo.save(usuario);
        return "redirect:/usuarios";
    }

    // ===== EDITAR USUARIO =====
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable("id") Integer id, Model model) {
        Optional<Usuario> opt = usuarioRepo.findById(id);

        if (opt.isPresent()) {
            Usuario usuario = opt.get();
            usuario.setPassword(null); // limpiar contraseña
            model.addAttribute("usuario", usuario);

            List<Rol> roles = rolRepo.findAll();
            model.addAttribute("roles", roles);

            return "form_usuarios";
        }
        return "redirect:/usuarios";
    }

    // ===== ELIMINAR USUARIO =====
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable("id") Integer id) {
        usuarioRepo.deleteById(id);
        return "redirect:/usuarios";
    }

    // ===== EXPORTAR A EXCEL =====
    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response,
                              @RequestParam(value = "filtroEstado", required = false) String filtroEstado) throws Exception {

        response.setContentType("application/vnd.ms-excel");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_easytime_" + System.currentTimeMillis() + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<Usuario> usuarios;
        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            usuarios = usuarioRepo.findByEstUser(estado, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        } else {
            usuarios = usuarioRepo.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        }

        response.getWriter().write("Simulación de contenido Excel para " + usuarios.size() + " usuarios. [PENDIENTE IMPLEMENTACIÓN DEL GENERADOR]");
    }

    // ===== EXPORTAR A PDF =====
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response,
                            @RequestParam(value = "filtroEstado", required = false) String filtroEstado) throws Exception {

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=usuarios_easytime_" + System.currentTimeMillis() + ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Usuario> usuarios;
        if (filtroEstado != null && !filtroEstado.isEmpty()) {
            boolean estado = Boolean.parseBoolean(filtroEstado);
            usuarios = usuarioRepo.findByEstUser(estado, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        } else {
            usuarios = usuarioRepo.findAll(PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        }

        response.getWriter().write("Simulación de contenido PDF para " + usuarios.size() + " usuarios. [PENDIENTE IMPLEMENTACIÓN DEL GENERADOR]");
    }
}