package com.easytime_java.repository;

import com.easytime_java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional; 

@Repository
// ⭐ CORRECCIÓN CLAVE: El tipo de la clave primaria (ID) ahora es Integer
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su correo electrónico.
  */
    Optional<Usuario> findByCorreoUser(String correoUser); 
}