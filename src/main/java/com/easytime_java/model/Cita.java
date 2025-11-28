package com.easytime_java.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA")
    private Integer idCita;

    @Column(name = "EST_CITA")
    private String estCita;

    @Column(name = "FECHA_CITA")
    private LocalDateTime fechaCita;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    @Column(name = "USUARIO_ID_USUARIO")
    private Integer usuarioIdUsuario;

    @Column(name = "SERVICIO_ID_SERVICIO")
    private Integer servicioIdServicio;

    // =========================
    // Getters y Setters
    // =========================

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public String getEstCita() {
        return estCita;
    }

    public void setEstCita(String estCita) {
        this.estCita = estCita;
    }

    public LocalDateTime getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDateTime fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public Integer getUsuarioIdUsuario() {
        return usuarioIdUsuario;
    }

    public void setUsuarioIdUsuario(Integer usuarioIdUsuario) {
        this.usuarioIdUsuario = usuarioIdUsuario;
    }

    public Integer getServicioIdServicio() {
        return servicioIdServicio;
    }

    public void setServicioIdServicio(Integer servicioIdServicio) {
        this.servicioIdServicio = servicioIdServicio;
    }
}
