package com.easytime_java.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Integer idUser;

    @Column(name = "NOM_USER")
    private String nombre;

    @Column(name = "APE_USER")
    private String apellido;

    @Column(name = "CORREO_USER")
    private String correo;

    @Column(name = "EST_USER")
    private Boolean estado = true;

    @Column(name = "NUMERO_DOC")
    private String documento;   // <-- cambiado a String

    @Column(name = "ROL_USER")
    private String rol;

    @Column(name = "TIPO_DOC")
    private String tipoDoc;

    @Column(name = "TEL_USER")
    private String telefono;

    @Column(name = "ID_ROL_USER", nullable = false)
    private Integer idRolUser;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATE_AT", nullable = false)
    private LocalDateTime updateAt;

    // --- constructores, getters y setters ---
    public Usuario() {}

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Boolean getEstado() { return estado; }
    public void setEstado(Boolean estado) { this.estado = estado; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Integer getIdRolUser() { return idRolUser; }
    public void setIdRolUser(Integer idRolUser) { this.idRolUser = idRolUser; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdateAt() { return updateAt; }
    public void setUpdateAt(LocalDateTime updateAt) { this.updateAt = updateAt; }
}
