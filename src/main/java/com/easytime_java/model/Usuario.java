package com.easytime_java.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
public class Usuario {

    // --- Columnas de la Base de Datos ---

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    private Integer idUser; 

    @Column(name = "NUMERO_DOC", length = 12)
    private String numeroDoc; 

    @Column(name = "TIPO_DOC") 
    private String tipoDoc; 

    @Column(name = "NOM_USER", length = 40)
    private String nomUser; 

    @Column(name = "APE_USER", length = 20)
    private String apeUser; 

    @Column(name = "TEL_USER", length = 20)
    private String telUser; 

    @Column(name = "CORREO_USER", length = 45)
    private String correoUser; 

    @Column(name = "EST_USER")
    private Boolean estUser = true; 

    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    // ⭐ CORRECCIÓN CRÍTICA: Cambiamos FetchType.LAZY a FetchType.EAGER
    // Esto resuelve el LazyInitializationException, asegurando que el objeto Rol
    // se cargue inmediatamente junto con el Usuario en la sesión.
    @ManyToOne(fetch = FetchType.EAGER) // <--- ¡CORREGIDO!
    @JoinColumn(name = "ID_ROL_USER")
    private Rol rol; 


    // Campos de auditoría
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATE_AT", nullable = false)
    private LocalDateTime updateAt;


    // --- Constructores, Getters y Setters ---

    public Usuario() {}

    public Integer getIdUser() { return idUser; }
    public void setIdUser(Integer idUser) { this.idUser = idUser; }

    public String getNomUser() { return nomUser; }
    public void setNomUser(String nomUser) { this.nomUser = nomUser; }

    public String getApeUser() { return apeUser; }
    public void setApeUser(String apeUser) { this.apeUser = apeUser; }

    public String getCorreoUser() { return correoUser; }
    public void setCorreoUser(String correoUser) { this.correoUser = correoUser; }

    public Boolean getEstUser() { return estUser; }
    public void setEstUser(Boolean estUser) { this.estUser = estUser; }

    public String getNumeroDoc() { return numeroDoc; }
    public void setNumeroDoc(String numeroDoc) { this.numeroDoc = numeroDoc; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }

    public String getTelUser() { return telUser; }
    public void setTelUser(String telUser) { this.telUser = telUser; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    // --- Getters y Setters de Auditoría (No Modificados) ---
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdateAt() { return updateAt; }
    public void setUpdateAt(LocalDateTime updateAt) { this.updateAt = updateAt; }
    
    
    // --- MÉTODOS AUXILIARES PARA FORMULARIOS ---
    public Integer getIdRolUser() {
        return (rol != null) ? rol.getIdRol() : null;
    }
    
    public void setIdRolUser(Integer idRol) {
        if (this.rol == null) {
            this.rol = new Rol();
        }
        
        this.rol.setIdRol(idRol);
    }
}