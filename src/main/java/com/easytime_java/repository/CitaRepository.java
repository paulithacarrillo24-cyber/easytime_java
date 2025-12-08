package com.easytime_java.repository;

import com.easytime_java.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface CitaRepository extends JpaRepository<Cita, Integer> {

    // Método existente: Verifica si ya existe una cita en la misma fecha y hora
    boolean existsByFechaCita(LocalDateTime fechaCita);

    // Método existente: Obtener las citas de un usuario por su id
    List<Cita> findByUsuarioIdUsuario(Integer idUser);

    // Obtener citas con fecha FUTURA (Activas), ordenadas ascendentemente por fecha
    // Usado por defecto por el Administrador
    List<Cita> findByFechaCitaAfterOrderByFechaCitaAsc(LocalDateTime fechaCita);

    // Obtener citas con fecha PASADA (Historial), ordenadas descendentemente por fecha
    // Usado por el Administrador cuando busca el historial
    List<Cita> findByFechaCitaBeforeOrderByFechaCitaDesc(LocalDateTime fechaCita);
    
    // ⭐ NUEVO MÉTODO ACTIVADO (Más eficiente para Cliente/Jefe de Patio):
    // Combina el filtro por usuario (findByUsuarioIdUsuario) y por fecha futura (AndFechaCitaAfter)
    List<Cita> findByUsuarioIdUsuarioAndFechaCitaAfterOrderByFechaCitaAsc(Integer idUser, LocalDateTime fechaCita);

     // --- Filtro multicriterio ---
    @Query("""
        SELECT c FROM Cita c
        WHERE (:servicioId IS NULL OR (c.servicio IS NOT NULL AND c.servicio.id = :servicioId))
          AND (:desde IS NULL OR c.fechaCita >= :desde)
          AND (:hasta IS NULL OR c.fechaCita <= :hasta)
          AND (:estado IS NULL OR (c.estCita = CASE WHEN :estado = 'Activo' THEN true ELSE false END))
          AND (:correoUsuario IS NULL OR LOWER(c.usuario.correoUser) LIKE LOWER(CONCAT('%', :correoUsuario, '%')))
        ORDER BY c.fechaCita ASC
    """)
    List<Cita> findByFilters(
            @Param("servicioId") Integer servicioId,
            @Param("desde") LocalDateTime desde,
            @Param("hasta") LocalDateTime hasta,
            @Param("estado") String estado,
            @Param("correoUsuario") String correoUsuario
    );
}
