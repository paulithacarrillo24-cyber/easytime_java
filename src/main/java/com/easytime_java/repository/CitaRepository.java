package com.easytime_java.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.easytime_java.model.Cita;

import java.time.LocalDateTime;

public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Verifica si ya existe una cita en la misma fecha y hora
    boolean existsByFechaCita(LocalDateTime fechaCita);
}
