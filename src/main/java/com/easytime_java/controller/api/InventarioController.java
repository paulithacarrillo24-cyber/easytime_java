package com.easytime_java.controller.api;

import com.easytime_java.model.Inventario;
import com.easytime_java.Service.InventarioService;
import com.easytime_java.Service.ProveedorService;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;
import org.springframework.core.io.ResourceLoader;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import com.lowagie.text.pdf.BaseFont;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.time.LocalDateTime;


@Controller
@RequestMapping("/inventarios")
public class InventarioController {

    private final InventarioService inventarioService;
    private final ProveedorService proveedorService;
    private final ISpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
    private final ServletContext servletContext;

    public InventarioController(
        InventarioService inventarioService,
        ProveedorService proveedorService,
        ISpringTemplateEngine templateEngine,
        ResourceLoader resourceLoader,
        ServletContext servletContext) {

    this.inventarioService = inventarioService;
    this.proveedorService = proveedorService;
    this.templateEngine = templateEngine;
    this.resourceLoader = resourceLoader;
    this.servletContext = servletContext;
}


    // LISTAR + FILTRO
    @GetMapping
    public String listar(@RequestParam(value = "q", required = false) String q, Model model) {

    if (q != null && !q.trim().isEmpty()) {
        model.addAttribute("inventarios", inventarioService.buscar(q));
    } else {
        model.addAttribute("inventarios", inventarioService.listar());
    }

    model.addAttribute("q", q);
    return "inventarios";
}

    @GetMapping("/crear")
    public String mostrarCrear(Model model) {
        model.addAttribute("inventario", new Inventario());
        model.addAttribute("listaProveedores", proveedorService.listar());
        return "form_inventario";
    }

    @PostMapping("/crear")
    public String crear(@ModelAttribute("inventario") Inventario inv) {

        inv.setUpdateAt(LocalDateTime.now());
        inventarioService.guardar(inv);

        return "redirect:/inventarios";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Integer id, Model model) {

        model.addAttribute("inventario", inventarioService.obtenerPorId(id));
        model.addAttribute("listaProveedores", proveedorService.listar());

        return "form_inventario";
    }

    @PostMapping("/{id}/editar")
    public String guardarEditar(@PathVariable Integer id, @ModelAttribute Inventario inv) {
        inv.setIdInventario(id);
        inv.setUpdateAt(LocalDateTime.now());

        inventarioService.guardar(inv);

        return "redirect:/inventarios";
    }

    // ===============================
    // 📄 GENERAR PDF
    // ===============================

    @GetMapping("/pdf")
    public void generarPdf(
            @RequestParam(value = "q", required = false) String q,
            HttpServletResponse response) throws Exception {

        // 1) obtener datos según filtro
        List<Inventario> lista = (q == null || q.isBlank()) 
            ? inventarioService.listar() 
            : inventarioService.buscar(q);

        // 2) preparar contexto Thymeleaf
        Context ctx = new Context();
        ctx.setVariable("inventarios", lista);
        ctx.setVariable("q", q);
        ctx.setVariable("fechaActual", LocalDateTime.now());

        String html = templateEngine.process("inventarios_pdf", ctx);

        // 3) preparar renderer
        ITextRenderer renderer = new ITextRenderer();

        // (opcional) registrar fuente TTF
        try {
            File fontFile = new File("src/main/resources/fonts/DejaVuSans.ttf");
            if (fontFile.exists()) {
                ITextFontResolver fontResolver = renderer.getFontResolver();
                fontResolver.addFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            }
        } catch (Exception ignored) {
            // si falla, no es crítico
        }

        // 4) configurar baseURL para recursos estáticos
        try {
            String basePath = new File("src/main/resources/static/").toURI().toURL().toString();
            renderer.getSharedContext().setBaseURL(basePath);
        } catch (Exception ignored) {
        }

        // 5) generar PDF y enviarlo en la respuesta
        renderer.setDocumentFromString(html);
        renderer.layout();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        renderer.createPDF(baos);
        renderer.finishPDF();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=inventarios_report.pdf");
        response.setContentLength(baos.size());
        baos.writeTo(response.getOutputStream());
        response.getOutputStream().flush();
    }
}
