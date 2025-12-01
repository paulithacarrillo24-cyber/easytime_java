package com.easytime_java.controller;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioRepository repo;

    public UsuarioController(UsuarioRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/exportar-pdf")
    public void exportarUsuariosPdf(HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=usuarios.pdf");

        List<Usuario> usuarios = repo.findAll();

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        // Título
        document.add(new Paragraph("Lista de Usuarios"));

        // Tabla SIN acciones
        PdfPTable table = new PdfPTable(5);
        table.addCell("ID");
        table.addCell("Nombre");
        table.addCell("Apellido");
        table.addCell("Correo");
        table.addCell("Documento");

        for (Usuario u : usuarios) {
            table.addCell(String.valueOf(u.getIdUser()));
            table.addCell(u.getNombre());
            table.addCell(u.getApellido());
            table.addCell(u.getCorreo());
            table.addCell(u.getTipoDoc() + " " + u.getDocumento());
        }

        document.add(table);
        document.close();
    }

    @GetMapping
    public String listar(Model model) {
        List<Usuario> usuarios = repo.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // template usuarios.html
    }

    @GetMapping("/buscar")
    public String buscar(@RequestParam("keyword") String keyword, Model model) {
        List<Usuario> usuarios = repo.buscarPorNombreODocumento(keyword);
        model.addAttribute("usuarios", usuarios);
        return "usuarios"; // vuelve a la misma vista con resultados filtrados
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
