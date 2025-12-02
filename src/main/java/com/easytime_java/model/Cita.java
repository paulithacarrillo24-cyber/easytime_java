package com.easytime_java.model;

import com.easytime_java.util.EstadoCitaConverter; // 💡 Importar el conversor
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "cita")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CITA")
    private Integer idCita;

    // 🔥 EST_CITA (TINYINT 1/2 en DB) -> Boolean (true/false en Java)
    @Column(name = "EST_CITA")
    @Convert(converter = EstadoCitaConverter.class)
    private Boolean estCita; // true=1 (Activo), false=2 (Inactivo)

    @Column(name = "FECHA_CITA")
    private LocalDateTime fechaCita;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    // 1. RELACIÓN con Usuario
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USUARIO_ID_USUARIO", insertable = false, updatable = false)
    private Usuario usuario;
    
    // Campo auxiliar para USUARIO_ID_USUARIO
    @Column(name = "USUARIO_ID_USUARIO")
    private Integer usuarioIdUsuario; 

    // 2. 💡 CORRECCIÓN CLAVE: RELACIÓN con Servicio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SERVICIO_ID_SERVICIO", insertable = false, updatable = false)
    private Servicio servicio; // <--- ¡Objeto Servicio para mostrar el nombre!

    // Campo auxiliar para SERVICIO_ID_SERVICIO (necesario para el formulario)
    @Column(name = "SERVICIO_ID_SERVICIO")
    private Integer servicioIdServicio; 

    // =========================
    // Constructores, Getters y Setters
    // =========================

    public Cita() {}

    // ----------------------------------------------------
    // GETTER / SETTER de ESTADO (Boolean)
    // ----------------------------------------------------
    public Boolean getEstCita() { return estCita; }
    public void setEstCita(Boolean estCita) { this.estCita = estCita; }

    // ----------------------------------------------------
    // GETTER / SETTER de SERVICIO (El Objeto)
    // ----------------------------------------------------
    public Servicio getServicio() { return servicio; }
    public void setServicio(Servicio servicio) { this.servicio = servicio; }

    // ----------------------------------------------------
    // GETTER / SETTER del ID de Servicio (El FK)
    // ----------------------------------------------------
    public Integer getServicioIdServicio() { return servicioIdServicio; }
    public void setServicioIdServicio(Integer servicioIdServicio) { this.servicioIdServicio = servicioIdServicio; }
    
    // ----------------------------------------------------
    // GETTER / SETTER del ID de Usuario
    // ----------------------------------------------------
    public Integer getUsuarioIdUsuario() { return usuarioIdUsuario; }
    public void setUsuarioIdUsuario(Integer usuarioIdUsuario) { this.usuarioIdUsuario = usuarioIdUsuario; }
    
    // --- Resto de Getters/Setters ---
    public Integer getIdCita() { return idCita; }
    public void setIdCita(Integer idCita) { this.idCita = idCita; }

    public LocalDateTime getFechaCita() { return fechaCita; }
    public void setFechaCita(LocalDateTime fechaCita) { this.fechaCita = fechaCita; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdateAt() { return updateAt; }
    public void setUpdateAt(LocalDateTime updateAt) { this.updateAt = updateAt; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}