package com.easytime_java.repository;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.easytime_java.model.Servicio;

public interface ServicioRepository extends JpaRepository<Servicio, Long> {
}
