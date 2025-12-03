package com.easytime_java.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "proveedor")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // ⭐ CORRECCIÓN: Usar ID_PROVEDOR (sin la segunda 'E') para coincidir con la DB.
    @Column(name = "ID_PROVEDOR") 
    private Integer idProveedor;

    @Column(name = "TIPO_DOC", nullable = false)
    private String tipoDoc; 
    
    // El campo NUM_PROV no se incluye, ya que no está en la estructura de la DB.

    @Column(name = "NOM_PROV", length = 45, nullable = false) 
    private String nomProv; 

    @Column(name = "TEL_PROV", length = 45) 
    private String telProv; 

    @Column(name = "EST_PROV") 
    private Integer estProv; 

    @Column(name = "CREATED_AT", nullable = false) 
    private LocalDateTime createdAt;

    @Column(name = "UPDATE_AT", nullable = false) 
    private LocalDateTime updateAt;

    // Constructor vacío
    public Proveedor() {}

    // --- Getters y Setters ---

    public Integer getIdProveedor() { return idProveedor; }
    public void setIdProveedor(Integer idProveedor) { this.idProveedor = idProveedor; }

    public String getTipoDoc() { return tipoDoc; }
    public void setTipoDoc(String tipoDoc) { this.tipoDoc = tipoDoc; }
    
    public String getNomProv() { return nomProv; }
    public void setNomProv(String nomProv) { this.nomProv = nomProv; }

    public String getTelProv() { return telProv; }
    public void setTelProv(String telProv) { this.telProv = telProv; }

    public Integer getEstProv() { return estProv; }
    public void setEstProv(Integer estProv) { this.estProv = estProv; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdateAt() { return updateAt; }
    public void setUpdateAt(LocalDateTime updateAt) { this.updateAt = updateAt; }
}