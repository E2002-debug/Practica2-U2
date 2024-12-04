package com.practica1.rest.models;

public class Proyecto {

    private Integer id;
    private String nombre;
    private String fechaInicio;
    private String fechaFin;
    private String estado;
    private Integer id_inversionista;
    private Integer id_propuesta;;

    public Proyecto() {
    }

    public Proyecto(Integer id, String nombre, String fechaInicio, String fechaFin, String estado,
            Integer id_inversionista, Integer id_propuesta) {
        this.id = id;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.id_inversionista = id_inversionista;
        this.id_propuesta = id_propuesta;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getId_inversionista() {
        return this.id_inversionista;
    }

    public void setId_inversionista(Integer value) {
        this.id_inversionista = value;
    }

    public Integer getId_propuesta() {
        return this.id_propuesta;
    }

    public void setId_propuesta(Integer value) {
        this.id_propuesta = value;
    }

    
}
