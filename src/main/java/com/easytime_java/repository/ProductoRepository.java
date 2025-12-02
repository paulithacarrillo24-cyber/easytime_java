package com.easytime_java.repository;

import com.easytime_java.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.inventario")
    List<Producto> findAllWithInventario();

} 