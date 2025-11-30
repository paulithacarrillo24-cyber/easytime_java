package com.easytime_java.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventario")
public class Inventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INVENTARIO", nullable = false)
    private Integer idInventario;

    @Column(name = "NOMBRE_PROD_INVE", length = 45, nullable = false)
    private String nombreProdInve;

    @Column(name = "CANTIDAD", length = 45, nullable = false)
    private String cantidad;

    @Column(name = "UPDATE_AT")
    private LocalDateTime updateAt;

    @Column(name = "PROVEEDOR_ID_PROV", nullable = false)
    private Integer proveedorId;

    public Inventario() {}

    // getters y setters
    public Integer getIdInventario() { return idInventario; }
    public void setIdInventario(Integer idInventario) { this.idInventario = idInventario; }

    public String getNombreProdInve() { return nombreProdInve; }
    public void setNombreProdInve(String nombreProdInve) { this.nombreProdInve = nombreProdInve; }

    public String getCantidad() { return cantidad; }
    public void setCantidad(String cantidad) { this.cantidad = cantidad; }

    public LocalDateTime getUpdateAt() { return updateAt; }
    public void setUpdateAt(LocalDateTime updateAt) { this.updateAt = updateAt; }

    public Integer getProveedorId() { return proveedorId; }
    public void setProveedorId(Integer proveedorId) { this.proveedorId = proveedorId; }
}
