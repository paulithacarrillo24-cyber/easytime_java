package com.easytime_java.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easytime_java.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreoUser(String correoUser);
}
