package com.easytime_java.repository;

import com.easytime_java.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    // Si necesitas búsquedas personalizadas luego, aquí se agregan.
}