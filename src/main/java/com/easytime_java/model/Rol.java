package com.easytime_java.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rol")
public class Rol {
    
    @Id
    @Column(name = "ID_ROL")
    private Integer idRol; // O Long, dependiendo del tipo de dato en la BD

    @Column(name = "NOM_ROL", length = 45)
    private String nomRol;

    // Constructor, Getters y Setters
    // ...
    public Integer getIdRol() { return idRol; }
    public void setIdRol(Integer idRol) { this.idRol = idRol; }
    public String getNomRol() { return nomRol; }
    public void setNomRol(String nomRol) { this.nomRol = nomRol; }
    // ...
}
