package com.easytime_java.config;

import com.easytime_java.Service.InventarioService;
import com.easytime_java.model.Inventario;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * Convierte el ID de Inventario (String) recibido del formulario HTML
 * en la entidad Inventario (Object) que espera la entidad Producto.
 */
@Component
public class InventarioConverter implements Converter<String, Inventario> {

    private final InventarioService inventarioService;

    // Inyección de dependencias del servicio de inventario
    public InventarioConverter(InventarioService inventarioService) {
        this.inventarioService = inventarioService;
    }

    @Override
    public Inventario convert(String source) {
        // 1. Manejar valores nulos o vacíos
        if (source == null || source.isEmpty()) {
            return null;
        }

        // 2. Intentar parsear el String a Integer (el ID)
        try {
            // El formulario envía el ID como String
            Integer id = Integer.parseInt(source);
            
            // 3. Buscar la entidad Inventario en la base de datos usando el ID
            // ASUMO que tu service tiene un método 'obtenerPorId(Integer id)'
            return inventarioService.obtenerPorId(id); 
            
        } catch (NumberFormatException e) {
            // Si el valor no es un número válido, retorna null o lanza una excepción
            System.err.println("Error de conversión: El ID de inventario no es un número válido: " + source);
            return null;
        }
    }
}