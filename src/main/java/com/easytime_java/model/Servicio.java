package com.easytime_java.model;

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

@Entity
@Table(name = "servicio")
@EntityListeners(AuditingEntityListener.class) //Audotoría (fechas de creación y edición)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Servicio {
    @Id
    @Column(unique = true, nullable = false)
    private Long ID_SERVICIO;

    @Column(nullable = false)
    private String NOM_SERV;

    @Column(nullable = false)
    private String DURACION_SERV;

    @Column(nullable = false)
    private Boolean EST_SERV;

    @Column(nullable = false)
    private String DESCP_SERV;

    @Column(nullable = false)
    private String PRECIO_SERV;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime CREATED_AT;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime UPDATE_AT;
}
