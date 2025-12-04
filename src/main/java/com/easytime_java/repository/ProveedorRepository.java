package com.easytime_java.repository;

import com.easytime_java.model.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer> {
    // JpaRepository ya incluye los métodos CRUD básicos (save, findAll, findById, delete)
}
