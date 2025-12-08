package com.easytime_java.repository;

import com.easytime_java.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface InventarioRepository extends JpaRepository<Inventario, Integer> {

    @Query("""
        SELECT i FROM Inventario i
        WHERE (:q IS NULL OR :q = '')
          OR LOWER(i.nombreProdInve) LIKE CONCAT('%', :q, '%')
          OR STR(i.cantidad) LIKE CONCAT('%', :q, '%')
          OR STR(i.proveedorId) LIKE CONCAT('%', :q, '%')
    """)
    List<Inventario> buscar(@Param("q") String q);
}
