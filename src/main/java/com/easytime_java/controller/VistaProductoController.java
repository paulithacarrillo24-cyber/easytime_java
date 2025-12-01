package com.easytime_java.controller;

import com.easytime_java.Service.InventarioService;
import com.easytime_java.Service.ProductoService;
import com.easytime_java.Service.ExcelService;
import com.easytime_java.Service.ReportService;

import com.easytime_java.model.Producto;
import com.easytime_java.exception.BusinessException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Controller
public class VistaProductoController {

    private final ProductoService service;
    private final InventarioService inventarioService;
    private final ExcelService excelService;
    private final ReportService reportService;

    public VistaProductoController(ProductoService service,
                                   InventarioService inventarioService,
                                   ExcelService excelService,
                                   ReportService reportService) {
        this.service = service;
        this.inventarioService = inventarioService;
        this.excelService = excelService;
        this.reportService = reportService;
    }

    // LISTAR
    @GetMapping("/adminproductos")
    public String listarProductos(@RequestParam(required = false) String codigo,
                              @RequestParam(required = false) String nombre,
                              @RequestParam(required = false) Integer inventarioId,
                              Model model) {

    List<Producto> productos = (codigo == null && nombre == null && inventarioId == null)
            ? service.listar()
            : service.listarFiltered(codigo, nombre, inventarioId);

    model.addAttribute("productos", productos);
    return "productos";
}

    @GetMapping("/productos/nuevo")
    public String mostrarFormularioNuevo(Model model) {
        model.addAttribute("producto", new Producto());
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    @PostMapping("/productos/guardar")
    public String guardarProducto(@ModelAttribute Producto producto, Model model) {
        try {

            service.guardar(producto);

            return "redirect:/adminproductos";

        } catch (BusinessException ex) {

            model.addAttribute("producto", producto);
            model.addAttribute("inventarios", inventarioService.listar());
            model.addAttribute("errorMessage", ex.getMessage());

            // Campo específico
            String msg = ex.getMessage().toLowerCase();
            if (msg.contains("código") || msg.contains("codigo")) {
                model.addAttribute("errorCodigo", ex.getMessage());
            }
            if (msg.contains("nombre")) {
                model.addAttribute("errorNombre", ex.getMessage());
            }

            return "form_productos";

        } catch (Exception ex) {

            model.addAttribute("producto", producto);
            model.addAttribute("inventarios", inventarioService.listar());
            model.addAttribute("errorMessage",
                    "Ocurrió un error inesperado: " + ex.getMessage());

            return "form_productos";
        }
    }

    @GetMapping("/productos/editar/{id}")
    public String editarProducto(@PathVariable Integer id, Model model) {
        Producto producto = service.obtenerPorId(id);
        model.addAttribute("producto", producto);
        model.addAttribute("inventarios", inventarioService.listar());
        return "form_productos";
    }

    @GetMapping("/productos/eliminar/{id}")
    public String eliminarProducto(@PathVariable Integer id) {
        service.eliminar(id);
        return "redirect:/adminproductos";
    }

    @GetMapping("/productos/pdf")
    public ResponseEntity<byte[]> generarPdfProductos() {
    byte[] pdf = reportService.generarReporteProductos();

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=productos.pdf")
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdf);
    }

    @GetMapping("/productos/excel")
public ResponseEntity<byte[]> descargarExcelProductos(
        @RequestParam(required = false) String codigo,
        @RequestParam(required = false) String nombre,
        @RequestParam(required = false) Integer inventarioId) {

    List<com.easytime_java.model.Producto> productos =
            (codigo == null && nombre == null && inventarioId == null)
                    ? service.listar()
                    : service.listarFiltered(codigo, nombre, inventarioId);

    byte[] excelBytes = excelService.generarExcelProductos(productos);

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.parseMediaType(
            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
    headers.setContentDispositionFormData("attachment", "productos.xlsx");

    return ResponseEntity.ok()
            .headers(headers)
            .body(excelBytes);
}
}
