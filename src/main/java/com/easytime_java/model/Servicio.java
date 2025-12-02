package com.easytime_java.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "servicio")
@EntityListeners(AuditingEntityListener.class) //Audotoría (fechas de creación y edición)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Servicio {
    @Id
    @NotNull(message = "El código es obligatorio")
    @Column(unique = true, nullable = false)
    private Long ID_SERVICIO;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    @Column(nullable = false, unique = true, length = 100)
    private String NOM_SERV;

    @Column(nullable = false)
    private String DURACION_SERV; // Con LocalTime como tipo de dato puedo hacer que trabaje en tiempo (en la BD el tipo de dato debe ser TIME), se debe importar import java.time.LocalTime;

    @Column(nullable = false)
    private Boolean EST_SERV;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 500, message = "La descripción no puede superar 500 caracteres")
    @Column(nullable = false, length = 500)
    private String DESCP_SERV;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor a 0")
    @Digits(integer = 8, fraction = 2, message = "Formato de precio inválido") //Integer define el número máximo de digitos enteros, fraction define el número máximo de dígitos decimales.
    @Column(nullable = false, precision = 10, scale = 2) //Hasta 10 dígitos, siendo los 2 últimos de estos decimales.
    private BigDecimal PRECIO_SERV;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime CREATED_AT;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime UPDATE_AT;
}
