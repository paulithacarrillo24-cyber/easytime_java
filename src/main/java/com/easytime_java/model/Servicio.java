package com.easytime_java.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

// Importaciones de Spring Data para Auditoría (correctas)
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Importaciones de Jakarta Persistence (correctas)
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*; // Para que las validaciones funcionen

@Entity
@Table(name = "servicio")
@EntityListeners(AuditingEntityListener.class)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Servicio {
    
    // 1. Clave Primaria: Consistente con Integer (para JpaRepository y FK en Cita)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_SERVICIO", unique = true, nullable = false) 
    private Integer idServicio;

    // 2. Nombre del Servicio: Mapeo correcto de NOM_SERV a nomServ
    @Column(name = "NOM_SERV", nullable = false)
    private String nomServ;

    @Column(name = "DURACION_SERV", nullable = false)
    private String duracionServ;

    // 3. Estado: Mapeo correcto de EST_SERV a Boolean
    @Column(name = "EST_SERV", nullable = false)
    private Boolean estServ;

    @Column(name = "DESCP_SERV", nullable = false)
    private String descpServ;

    //@DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    //@Digits(integer = 8, fraction = 2, message = "Formato de precio inválido")
    @Column(name = "PRECIO_SERV", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioServ;

    // 4. Auditoría de Creación
    @CreatedDate
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // 5. Auditoría de Modificación (Mantenemos nullable=false si la DB garantiza un valor)
    @LastModifiedDate
    @Column(name = "UPDATE_AT", nullable = true) // 🔥 AJUSTE: updateAt DEBE ser nullable=true 
    // si se permite nulo en la DB antes de la primera actualización.
    private LocalDateTime updateAt;
}