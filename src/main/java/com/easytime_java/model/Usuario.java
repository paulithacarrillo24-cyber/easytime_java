package com.easytime_java.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class) //Audotoría (fechas de creación y edición)
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID_USER;

    @Column(nullable = false)
    private String TIPO_DOC;

    @Column(unique = true, nullable = false)
    private Long NUMERO_DOC;

    @Column(nullable = false)
    private String NOM_USER;

    @Column(nullable = false)
    private String APE_USER;

    @Column(unique = true, nullable = false)
    private long TEL_USER;

    @Column(name = "CORREO_USER", nullable = false, unique = true)
    private String correoUser;

    @Column(nullable = false)
    private String ROL_USER; // Administrador, Jefe de patio y Cliente

    @Column(nullable = false, length = 60)
    private String CONTRA_USER;

    @Column(nullable = false)
    private Boolean EST_USER;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime CREATED_AT;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime UPDATE_AT;
    
}
