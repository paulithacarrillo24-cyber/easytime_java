package com.easytime_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; 

import com.easytime_java.model.Servicio;

@Repository 
public interface ServicioRepository extends JpaRepository<Servicio, Integer> { 
    // La corrección es usar Integer aquí para coincidir con la entidad Servicio.java 
    // y para que funcione con los @PathVariable en ServiciosController.
}