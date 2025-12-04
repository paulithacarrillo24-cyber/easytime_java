package com.easytime_java.repository;

import com.easytime_java.model.Servicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicioRepository extends JpaRepository<Servicio, Integer> {

    @Query("""
        SELECT s FROM Servicio s
        WHERE (:q IS NULL OR :q = '')
           OR LOWER(s.nomServ) LIKE CONCAT('%', :q, '%')
           OR LOWER(s.descpServ) LIKE CONCAT('%', :q, '%')
           OR STR(s.precioServ) LIKE CONCAT('%', :q, '%')
           OR STR(s.duracionServ) LIKE CONCAT('%', :q, '%')
    """)
    List<Servicio> buscar(@Param("q") String q);
}
