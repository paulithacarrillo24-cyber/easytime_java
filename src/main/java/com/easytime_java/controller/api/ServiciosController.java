package com.easytime_java.controller.api;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.easytime_java.model.Servicio;
import com.easytime_java.repository.ServicioRepository;
import com.lowagie.text.pdf.BaseFont;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/servicios")
public class ServiciosController {

    @Autowired
    private ServicioRepository repo;

    @Autowired
    private TemplateEngine templateEngine;

    // LISTAR + FILTRO
    @GetMapping
    public String listarServicios(@RequestParam(value = "q", required = false) String q, Model model) {

        List<Servicio> lista;

        if (q == null || q.trim().isEmpty()) {
            lista = repo.findAll();
        } else {
            q = q.toLowerCase();
            lista = repo.buscar(q.toLowerCase().trim());
        }

        model.addAttribute("servicios", lista);
        model.addAttribute("q", q);

        return "servicios";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("servicio", new Servicio());
        return "form_servicios";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute Servicio servicio) {
        repo.save(servicio);
        return "redirect:/servicios?guardado=true";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Integer id, Model model) {
        model.addAttribute("servicio", repo.findById(id).orElseThrow());
        return "form_servicios";
    }

    @PostMapping("/eliminar/{id}")
        public String eliminar(@PathVariable Integer id) {
        repo.deleteById(id);
        return "redirect:/servicios";
    }

    @GetMapping("/pdf")
    public void generarPdf(@RequestParam(value = "q", required = false) String q,
                       HttpServletResponse response) {
    // IMPORTANTE: no lanzar excepción sin control aquí, capturamos todo y devolvemos 500 con log
    try {
        List<Servicio> servicios;
        if (q == null || q.trim().isEmpty()) {
            servicios = repo.findAll();
        } else {
            servicios = repo.buscar(q.toLowerCase().trim());
        }

        // 1) preparar contexto Thymeleaf
        Context ctx = new Context();
        ctx.setVariable("servicios", servicios);
        ctx.setVariable("q", q);
        ctx.setVariable("fechaActual", java.time.LocalDateTime.now());

        // 2) renderizar HTML (revisar output si hay problemas)
        String html = templateEngine.process("servicios_pdf", ctx);
        if (html == null || html.isBlank()) {
            // plantilla vacía -> falla
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Plantilla Thymeleaf produjo HTML vacío");
            return;
        }

        // Opcional: guardar HTML en temp para inspección (descomenta si necesitas)
        // java.nio.file.Files.write(java.nio.file.Path.of("debug_servicios.html"), html.getBytes(StandardCharsets.UTF_8));

        // 3) preparar renderer
        ITextRenderer renderer = new ITextRenderer();

        // Registrar fuente (seguro: solo si existe)
        try {
            File fontFile = new File("src/main/resources/fonts/DejaVuSans.ttf");
            if (fontFile.exists() && fontFile.isFile()) {
                ITextFontResolver fontResolver = renderer.getFontResolver();
                fontResolver.addFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        } catch (Throwable fe) {
            // no interrumpe la generación, solo loguea
            fe.printStackTrace();
        }

        // baseURL para recursos estáticos (si usas CSS/IMGS en la plantilla)
        try {
            String basePath = new File("src/main/resources/static/").toURI().toURL().toString();
            renderer.getSharedContext().setBaseURL(basePath);
        } catch (Throwable be) {
            // si falla, no abortamos; CSS/images podrían no resolverse
            be.printStackTrace();
        }

        // 4) convertir a PDF en memoria
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(baos);
            renderer.finishPDF();
        } catch (Throwable renderEx) {
            // captura errores del renderer (habitual fuente/doctype/invalid xhtml)
            renderEx.printStackTrace();
            // opcional: escribir el HTML a disco para inspección
            try {
                java.nio.file.Files.write(java.nio.file.Path.of("debug_servicios.html"), html.getBytes(java.nio.charset.StandardCharsets.UTF_8));
            } catch (Exception ignored) {}
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generando PDF: " + renderEx.getMessage());
            return;
        }

        // 5) respuestas seguras al cliente
        byte[] pdfBytes = baos.toByteArray();
        if (pdfBytes == null || pdfBytes.length == 0) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "PDF vacío generado (EOF)");
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=servicios_report.pdf");
        response.setContentLength(pdfBytes.length);

        try (var out = response.getOutputStream()) {
            out.write(pdfBytes);
            out.flush();
        } catch (Throwable outEx) {
            outEx.printStackTrace();
            // no podemos hacer mucho si escribir al response falla
        } finally {
            try { baos.close(); } catch (Exception ignored) {}
        }

    } catch (Exception e) {
        // catch global: registrar y devolver 500
        e.printStackTrace();
        try {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error inesperado generando PDF: " + e.getMessage());
        } catch (Exception ignored) {}
    }
}

}