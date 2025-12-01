package com.easytime_java.repository;

import com.easytime_java.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Integer>{

    @Query("SELECT p FROM Producto p LEFT JOIN FETCH p.inventario")
    List<Producto> findAllWithInventario();

    Optional<Producto> findByCodigo(String codigo);
    boolean existsByCodigo(String codigo);

} 
