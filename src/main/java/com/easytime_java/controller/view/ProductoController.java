package com.easytime_java.controller.view;

import com.easytime_java.Service.InventarioService;
import com.easytime_java.Service.ProductoService;
import com.easytime_java.model.Producto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.core.io.ResourceLoader;

import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.ISpringTemplateEngine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.math.BigDecimal;
import java.util.List;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;

import org.xhtmlrenderer.pdf.ITextRenderer;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import com.lowagie.text.pdf.BaseFont;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService service;
    private final InventarioService inventarioService;
    private final ISpringTemplateEngine templateEngine;
    private final ResourceLoader resourceLoader;
    private final ServletContext servletContext;

    public ProductoController(ProductoService service,
                              InventarioService inventarioService,
                              ISpringTemplateEngine templateEngine,
                              ResourceLoader resourceLoader,
                              ServletContext servletContext) {
        this.service = service;
        this.inventarioService = inventarioService;
        this.templateEngine = templateEngine;
        this.resourceLoader = resourceLoader;
        this.servletContext = servletContext;
    }


    // CREAR - Mostrar Formulario
    @GetMapping("/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    // CREAR/EDITAR - Guardar
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto) {
        service.guardar(producto);
        return "redirect:/productos";
    }

    // EDITAR - Mostrar Formulario
    @GetMapping("/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = service.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    // ELIMINAR (POST)
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/productos";
    }

   // LISTAR con q
    @GetMapping
    public String listarProductos(@RequestParam(required = false) String q, Model model) {
        model.addAttribute("productos", service.buscar(q));
        model.addAttribute("inventarios", inventarioService.listar()); // si lo usas en la vista
        model.addAttribute("q", q);
        return "productos";
    }

    // EXPORTAR PDF respetando q
    @GetMapping("/pdf")
    public void exportPdf(@RequestParam(required = false) String q,
                      HttpServletResponse response) throws Exception {

    List<Producto> productos = service.buscar(q);

    Context ctx = new Context();
    ctx.setVariable("productos", productos);
    ctx.setVariable("f_q", q);
    ctx.setVariable("isPdf", true);

    String html = templateEngine.process("productos_pdf", ctx);

    ITextRenderer renderer = new ITextRenderer();
    ITextFontResolver fontResolver = renderer.getFontResolver();

    try {
        File fontFile = new File("src/main/resources/fonts/DejaVuSans.ttf");
        if (!fontFile.exists()) {
            var res = resourceLoader.getResource("classpath:fonts/DejaVuSans.ttf");
            if (res.exists()) {
                File tmp = File.createTempFile("dejavu", ".ttf");
                try (var is = res.getInputStream(); var os = new java.io.FileOutputStream(tmp)) {
                    is.transferTo(os);
                }
                fontFile = tmp;
            }
        }
        if (fontFile.exists()) {
            fontResolver.addFont(fontFile.getAbsolutePath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    String basePath;
    try {
        basePath = new File("src/main/resources/static/").toURI().toURL().toString();
    } catch (Exception e) {
        String contextPath = servletContext.getRealPath("/");
        if (contextPath != null) {
            basePath = new File(contextPath).toURI().toURL().toString();
        } else {
            basePath = "";
        }
    }

    if (basePath != null && !basePath.isBlank()) {
        renderer.getSharedContext().setBaseURL(basePath);
    }

    renderer.setDocumentFromString(html);
    renderer.layout();

    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    renderer.createPDF(baos);
    renderer.finishPDF();

    response.setContentType("application/pdf");
    response.setHeader("Content-Disposition", "attachment; filename=productos_report.pdf");
    response.setContentLength(baos.size());
    baos.writeTo(response.getOutputStream());
    response.getOutputStream().flush();
}

}
