package com.easytime_java.model;

/**
 * Enumeración que representa los tipos de documentos de identificación
 * utilizados en el sistema (basado en los códigos de la base de datos).
 */
public enum TipoDocumento {

    // Tipos de documentos basados en los códigos proporcionados en la imagen:
    CEDULA_CIUDADANIA("CC", "Cédula de Ciudadanía"),
    CEDULA_EXTRANJERIA("CE", "Cédula de Extranjería"),
    TARJETA_IDENTIDAD("TI", "Tarjeta de Identidad"),
    PASAPORTE("PA", "Pasaporte"),
    PERMISO_PROTECCION_TEMPORAL("PPT", "Permiso por Protección Temporal");


    private final String codigo;
    private final String descripcion;

    /**
     * Constructor para TipoDocumento.
     * @param codigo El código corto que se almacena en la base de datos (ej. "CC").
     * @param descripcion La descripción completa para el usuario (ej. "Cédula de Ciudadanía").
     */
    TipoDocumento(String codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el código corto asociado al tipo de documento.
     * @return El código de la base de datos.
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Obtiene la descripción completa asociada al tipo de documento.
     * @return La descripción legible.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Método estático para buscar un TipoDocumento por su código.
     * @param codigo El código corto del documento (ej. "PA").
     * @return El TipoDocumento correspondiente.
     * @throws IllegalArgumentException si el código proporcionado no existe.
     */
    public static TipoDocumento buscarPorCodigo(String codigo) {
        if (codigo == null || codigo.trim().isEmpty()) {
            throw new IllegalArgumentException("El código no puede ser nulo o vacío.");
        }
        for (TipoDocumento tipo : values()) {
            if (tipo.codigo.equalsIgnoreCase(codigo.trim())) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código de TipoDocumento inválido: " + codigo);
    }
}
