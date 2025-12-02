package com.easytime_java.repository;

import com.easytime_java.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    
    // Spring Data JPA ya incluye métodos como:
    // - findAll()
    // - findById(Integer id)
    // - save(Rol rol)
}
