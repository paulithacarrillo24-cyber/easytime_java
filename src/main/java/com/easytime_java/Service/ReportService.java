package com.easytime_java.Service;

import com.easytime_java.model.Producto;
import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final ProductoService productoService;

    public ReportService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public byte[] generarReporteProductos() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Listado de productos"));
        document.add(new Paragraph(" ")); // espacio

        PdfPTable table = new PdfPTable(8); // columnas
        table.setWidthPercentage(100);

        addTableHeader(table);

        List<Producto> productos = productoService.listar();
        for (Producto p : productos) {
            addRow(table, p);
        }

        document.add(table);
        document.close();
        return out.toByteArray();
    }

    private void addTableHeader(PdfPTable table) {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
        String[] headers = {"ID", "Código", "Nombre", "Descripción", "Caducidad", "Precio", "Cantidad", "Inventario"};
        for (String h : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(h, headFont));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setPadding(4f);
            table.addCell(cell);
        }
    }

    private void addRow(PdfPTable table, Producto p) {
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
        table.addCell(cell(p.getIdProducto() == null ? "" : p.getIdProducto().toString(), cellFont));
        table.addCell(cell(p.getCodigo(), cellFont));
        table.addCell(cell(p.getNombre(), cellFont));
        table.addCell(cell(p.getDescripcion(), cellFont));
        String cad = (p.getCaducidad() == null) ? "" : p.getCaducidad().format(DATE_FMT);
        table.addCell(cell(cad, cellFont));
        table.addCell(cell(p.getPrecio() == null ? "" : p.getPrecio().toString(), cellFont));
        table.addCell(cell(p.getCantidad() == null ? "" : p.getCantidad().toString(), cellFont));
        String invName = (p.getInventario() != null && p.getInventario().getNombreProdInve() != null)
                ? p.getInventario().getNombreProdInve() : "";
        table.addCell(cell(invName, cellFont));
    }

    private PdfPCell cell(String text, Font font) {
        PdfPCell c = new PdfPCell(new Phrase(text == null ? "" : text, font));
        c.setPadding(4f);
        return c;
    }
}
