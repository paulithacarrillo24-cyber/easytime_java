package com.easytime_java.Service;

import com.easytime_java.model.Usuario;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

// NOTA: Esta clase requiere una dependencia de PDF (ej: iText, OpenPDF, o Apache FOP) en tu pom.xml
@Service
public class PdfGenerator {

    /**
     * Genera un archivo PDF con la lista de usuarios y lo escribe en el OutputStream.
     *
     * @param usuarios La lista de usuarios a exportar.
     * @param outputStream El stream de salida de la respuesta HTTP.
     * @throws Exception Si ocurre un error al generar el archivo.
     */
    public void export(List<Usuario> usuarios, OutputStream outputStream) throws Exception {
        // --- INICIO DE LA SIMULACIÓN ---
        // En una implementación real, aquí usarías una librería de PDF para crear
        // la estructura del documento.
        
        String content = "REPORTE DE USUARIOS - EasyTime\n\n";
        content += "Total de Usuarios: " + usuarios.size() + "\n\n";
        
        for (Usuario u : usuarios) {
            String estado = u.getEstUser() ? "Activo" : "Inactivo";
            String rol = u.getRol() != null ? u.getRol().getNomRol() : "N/A";
            content += String.format("ID: %d | Nombre: %s %s | Correo: %s | Rol: %s | Estado: %s\n",
                    u.getIdUser(),
                    u.getNomUser(), 
                    u.getApeUser(),
                    u.getCorreoUser(),
                    rol,
                    estado);
        }
        
        // Simulación: Escribir contenido simple.
        outputStream.write(content.getBytes());
        outputStream.flush();
        // --- FIN DE LA SIMULACIÓN ---
    }
}
