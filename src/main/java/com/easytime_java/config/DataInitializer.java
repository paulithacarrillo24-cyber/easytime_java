package com.easytime_java.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.easytime_java.model.Usuario;
import com.easytime_java.repository.UsuarioRepository;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initDatabase(UsuarioRepository repo) {
        return args -> {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            // Usuario Administrador
            if (repo.findByCorreoUser("admin@correo.com").isEmpty()) {
                Usuario admin = new Usuario();
                admin.setTIPO_DOC("Cédula Ciudadania");                          // tipo de documento
                admin.setNUMERO_DOC(145963231L);                    // número de documento
                admin.setNOM_USER("Admin");                       // nombre
                admin.setAPE_USER("System");                      // apellido
                admin.setTEL_USER(3001112233L);                   // teléfono
                admin.setCorreoUser("admin@correo.com");          // correo
                admin.setCONTRA_USER(encoder.encode("123"));      // contraseña encriptada
                admin.setROL_USER("Administrador");               // rol
                admin.setEST_USER(true);                          // estado activo

                repo.save(admin);
                System.out.println("Usuario admin creado con éxito");
            } else {
                System.out.println("Usuario admin ya existe");
            }

            // Usuario Cliente
            if (repo.findByCorreoUser("cliente@correo.com").isEmpty()) {
                Usuario cliente = new Usuario();
                cliente.setTIPO_DOC("Cédula Ciudadania");                        // tipo de documento
                cliente.setNUMERO_DOC(2000002L);                  // número de documento
                cliente.setNOM_USER("Cliente");                   // nombre
                cliente.setAPE_USER("Demo");                      // apellido
                cliente.setTEL_USER(3002223344L);                 // teléfono
                cliente.setCorreoUser("cliente@correo.com");      // correo
                cliente.setCONTRA_USER(encoder.encode("123"));    // contraseña encriptada
                cliente.setROL_USER("Cliente");                   // rol
                cliente.setEST_USER(true);                        // estado activo

                repo.save(cliente);
                System.out.println("Usuario cliente creado con éxito");
            } else {
                System.out.println("Usuario cliente ya existe");
            }
        };
    }
}