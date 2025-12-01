package com.easytime_java.Service;

import com.easytime_java.model.Producto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class ExcelService {

    public byte[] generarExcelProductos(List<Producto> productos) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Productos");
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("ID");
            header.createCell(1).setCellValue("Código");
            header.createCell(2).setCellValue("Nombre");
            header.createCell(3).setCellValue("Descripción");
            header.createCell(4).setCellValue("Caducidad");
            header.createCell(5).setCellValue("Precio");
            header.createCell(6).setCellValue("Cantidad");
            header.createCell(7).setCellValue("Inventario");

            int rowIdx = 1;

            for (Producto p : productos) {
                Row row = sheet.createRow(rowIdx++);

                row.createCell(0).setCellValue(
                        p.getIdProducto() == null ? "" : String.valueOf(p.getIdProducto())
                );
                row.createCell(1).setCellValue(p.getCodigo());
                row.createCell(2).setCellValue(p.getNombre());
                row.createCell(3).setCellValue(p.getDescripcion());
                row.createCell(4).setCellValue(
                        p.getCaducidad() == null ? "" : p.getCaducidad().toString()
                );
                row.createCell(5).setCellValue(p.getPrecio());
                row.createCell(6).setCellValue(p.getCantidad());
                row.createCell(7).setCellValue(
                        p.getInventario() == null ? "" :
                        String.valueOf(p.getInventario().getIdInventario())
                );
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            wb.write(out);
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando Excel: " + e.getMessage());
        }
    }
}
