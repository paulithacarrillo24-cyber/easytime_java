package com.easytime_java.Service;

import com.easytime_java.model.Usuario;
import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

// NOTA: Esta clase requiere la dependencia de Apache POI (o similar) en tu pom.xml
@Service
public class ExcelGenerator {

    /**
     * Genera un archivo Excel (XLSX) con la lista de usuarios y lo escribe en el OutputStream.
     *
     * @param usuarios La lista de usuarios a exportar.
     * @param outputStream El stream de salida de la respuesta HTTP.
     * @throws Exception Si ocurre un error al generar el archivo.
     */
    public void export(List<Usuario> usuarios, OutputStream outputStream) throws Exception {
        // --- INICIO DE LA SIMULACIÓN ---
        // En una implementación real, aquí usarías la librería Apache POI para crear
        // el archivo Excel.
        
        String content = "ID\tNombre\tCorreo\tRol\tEstado\n";
        for (Usuario u : usuarios) {
            String estado = u.getEstUser() ? "Activo" : "Inactivo";
            String rol = u.getRol() != null ? u.getRol().getNomRol() : "N/A";
            content += String.format("%d\t%s %s\t%s\t%s\t%s\n",
                    u.getIdUser(),
                    u.getNomUser(), 
                    u.getApeUser(),
                    u.getCorreoUser(),
                    rol,
                    estado);
        }
        
        outputStream.write(content.getBytes());
        outputStream.flush();
        // --- FIN DE LA SIMULACIÓN ---
    }
}
