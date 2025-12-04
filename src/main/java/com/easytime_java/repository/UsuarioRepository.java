package com.easytime_java.repository;

import com.easytime_java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional; 

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su correo electrónico (usado para login/seguridad).
     */
    Optional<Usuario> findByCorreoUser(String correoUser); 

    /**
     * Filtra la lista de usuarios por su estado (activo/inactivo) y la ordena.
     * Necesario para el filtrado en el listado y en la exportación.
     */
    List<Usuario> findByEstUser(Boolean estUser, Sort sort);
}