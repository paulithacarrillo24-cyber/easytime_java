package com.easytime_java.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.easytime_java.model.Servicio;
import com.easytime_java.service.ServiciosService;
import com.easytime_java.utils.PdfGenerator;

import jakarta.servlet.http.HttpServletResponse;

/*
* Controlador encargado de manejar las vistas y reportes relacionados con los
* clientes.
* Proporciona endpoints para listar clientes filtrados, listar todos los
* clientes
* y generar un reporte en PDF.
*/
@Controller

public class ReporteController {

    private final ServiciosService servicio;
    private final PdfGenerator pdfGenerator;

    public ReporteController(ServiciosService servicio, PdfGenerator pdfGenerator) {
        this.servicio = servicio;
        this.pdfGenerator = pdfGenerator;
    }
    
    @GetMapping("/vista_servicios")
        public String vistaServicios(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
        Model model) {
        List<Servicio> servicios = servicio.filtrarServicios(nombre, desde, hasta);
        model.addAttribute("servicios", servicios);
        model.addAttribute("nombre", nombre);
        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        return "vista_servicios";
    }

    @GetMapping("/vista_servicios/todos")
        public String vistaServicios(Model model) {
        var servicios = servicio.listarTodos();
        model.addAttribute("servicios", servicios);
        return "vista_servicios";
        }

    @GetMapping("/reporte_servicios")
        public void generarReporte(
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta,
        HttpServletResponse response) throws Exception {
            var servicios = servicio.filtrarServicios(nombre, desde, hasta);
            pdfGenerator.generarPdf("reporte_servicios", servicios, desde, hasta, response);
        }
}
