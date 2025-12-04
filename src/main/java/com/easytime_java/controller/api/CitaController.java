package com.easytime_java.controller.api;

import com.easytime_java.model.Cita;
import com.easytime_java.repository.CitaRepository;
import com.easytime_java.repository.ServicioRepository;
import com.easytime_java.repository.UsuarioRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import org.springframework.core.io.ResourceLoader;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletContext;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import com.lowagie.text.pdf.BaseFont;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/citas")
public class CitaController {

    private final CitaRepository citaRepository;
    private final UsuarioRepository usuarioRepository;
    private final ServicioRepository servicioRepository;
    private final ISpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
    private final ServletContext servletContext;

    public CitaController(CitaRepository citaRepository,
                          UsuarioRepository usuarioRepository,
                          ServicioRepository servicioRepository,
                          ISpringTemplateEngine templateEngine,
                          ResourceLoader resourceLoader,
                          ServletContext servletContext) {
        this.citaRepository = citaRepository;
        this.usuarioRepository = usuarioRepository;
        this.servicioRepository = servicioRepository;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }

    // --- LISTADO CON FILTROS SIMPLES ---
    @GetMapping("/lista")
        public String listarCitas(Model model,
                                @RequestParam Optional<Boolean> estado) {

            List<Cita> citas = citaRepository.findAll().stream()
                .filter(c -> estado.map(e -> c.getEstCita() != null && c.getEstCita().equals(e)).orElse(true))
                .collect(Collectors.toList());

            model.addAttribute("citas", citas);
            model.addAttribute("f_estado", estado.orElse(null));

            return "citas";
        }



    // --- GENERAR PDF CON LOS MISMOS FILTROS ---
    @GetMapping("/pdf")
        public void generarPdf(@RequestParam Optional<Boolean> estado,
                            HttpServletResponse response) throws Exception {

            List<Cita> citas = citaRepository.findAll().stream()
                .filter(c -> estado.map(e -> c.getEstCita() != null && c.getEstCita().equals(e)).orElse(true))
                .collect(Collectors.toList());

            Context ctx = new Context();
            ctx.setVariable("citas", citas);
            ctx.setVariable("titulo", "Reporte de Citas");
            ctx.setVariable("fechaGeneracion", LocalDateTime.now());

            String html = templateEngine.process("citas_pdf_template", ctx);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html, "file:///" + servletContext.getRealPath("/"));
            renderer.layout();

            try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                renderer.createPDF(baos);
                renderer.finishPDF();

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=reporte_citas.pdf");
                response.setContentLength(baos.size());
                baos.writeTo(response.getOutputStream());
                response.getOutputStream().flush();
            }
        }
}
