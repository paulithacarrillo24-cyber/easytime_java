package com.easytime_java.repository;

import com.easytime_java.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.inventario")
    List<Producto> findAllWithInventario();

    @Query("""
           SELECT p FROM Producto p
           LEFT JOIN FETCH p.inventario inv
           WHERE (:q IS NULL OR :q = '' 
               OR LOWER(p.nombre) LIKE %:q% 
               OR LOWER(p.codigo) LIKE %:q% 
               OR LOWER(p.descripcion) LIKE %:q%
               OR LOWER(inv.nombreProdInve) LIKE %:q%
           )
           """)
    List<Producto> buscarPorTermino(@Param("q") String q);
}
