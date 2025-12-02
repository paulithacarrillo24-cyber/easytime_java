package com.easytime_java.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convierte el estado de la cita entre Boolean (Java) y Integer (Base de Datos TINYINT).
 * - TRUE (Activo)  <--> 1
 * - FALSE (Inactivo) <--> 2
 */
@Converter(autoApply = false)
public class EstadoCitaConverter implements AttributeConverter<Boolean, Integer> {

    /**
     * Convierte de Java (Boolean) a Base de Datos (Integer/TINYINT).
     */
    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        // Si es TRUE (Activo), retorna 1. Si es FALSE (Inactivo), retorna 2.
        return attribute ? 1 : 2; 
    }

    /**
     * Convierte de Base de Datos (Integer/TINYINT) a Java (Boolean).
     */
    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        // Si el valor de la DB es 1, retorna TRUE. Para cualquier otro valor (asumimos 2), retorna FALSE.
        return dbData.equals(1); 
    }
}