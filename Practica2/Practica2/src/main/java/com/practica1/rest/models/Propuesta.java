package com.practica1.rest.models;

public class Propuesta {

    private Integer id;
    private String descripcion;
    private Float monto;
    private Float watsGenerados;

    public Propuesta() {
    }

    public Propuesta(Integer id, String descripcion, Float monto, Float watsGenerados) {
        this.id = id;
        this.descripcion = descripcion;
        this.monto = monto;
        this.watsGenerados = watsGenerados;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer value) {
        this.id = value;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    public Float getMonto() {
        return this.monto;
    }

    public void setMonto(Float value) {
        this.monto = value;
    }

    public Float getWatsGenerados() {
        return this.watsGenerados;
    }

    public void setWatsGenerados(Float value) {
        this.watsGenerados = value;
    }

    @Override
    public String toString() {
        return "Propuesta{" +
                "id=" + id +
                ", descripcion='" + descripcion + '\'' +
                ", monto=" + monto +
                ", watsGenerados=" + watsGenerados +
                '}';
    }
}
