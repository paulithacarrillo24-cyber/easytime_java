package com.easytime_java.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.easytime_java.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    /**
     * Busca un usuario por su correo electrónico (usado para login/seguridad).
     */
    Optional<Usuario> findByCorreoUser(String correoUser);

    /**
     * Filtra usuarios por estado (activo/inactivo) con paginación.
     */
    Page<Usuario> findByEstUser(Boolean estUser, Pageable pageable);

    /**
     * Filtra usuarios por nombre o correo con paginación.
     */
    Page<Usuario> findByNomUserContainingIgnoreCaseOrCorreoUserContainingIgnoreCase(
            String nombre, String correo, Pageable pageable
    );
}