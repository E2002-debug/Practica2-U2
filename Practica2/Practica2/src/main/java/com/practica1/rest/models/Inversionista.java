package com.practica1.rest.models;

public class Inversionista {

    private Integer id;
    private String nombre;
    private String apellido;
    private String dni;
    private Float montoI;

    public Inversionista() {
    }

    public Inversionista(Integer id, String nombre, String apellido, String dni, Float montoI) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.montoI = montoI;
        this.dni = dni;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return this.apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Float getMontoI() {
        return this.montoI;
    }

    public void setMontoI(Float montoI) {
        this.montoI = montoI;
    }


  @Override
    public String toString() {
        return "Inversionista{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", montoI=" + montoI +
                '}';
    }
}
