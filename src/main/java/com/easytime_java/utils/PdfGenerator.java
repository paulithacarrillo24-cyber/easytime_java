package com.easytime_java.utils;

import java.io.OutputStream;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.xhtmlrenderer.pdf.ITextRenderer;

import freemarker.template.Template;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class PdfGenerator {

    private final FreeMarkerConfigurer configurer;

    // Constructor de la clase PdfGenerator.
    public PdfGenerator(FreeMarkerConfigurer configurer) {
        this.configurer = configurer;
    }

    public void generarPdf(String templateName, List<?> datos, LocalDate desde, LocalDate hasta,
                           HttpServletResponse response) throws Exception {
        Map<String, Object> model = new HashMap<>();
        model.put("servicios", datos);   // ahora usa servicios
        model.put("desde", desde);
        model.put("hasta", hasta);

        Template template = configurer.getConfiguration().getTemplate(templateName + ".html");
        String html = FreeMarkerTemplateUtils.processTemplateIntoString(template, model);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=servicios.pdf"); // nombre coherente

        OutputStream out = response.getOutputStream();
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(out);
        out.close();
    }
}