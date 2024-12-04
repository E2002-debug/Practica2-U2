package com.practica1.rest.controller.dao.services;

import java.util.HashMap;
import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Propuesta;
import com.practica1.rest.models.Proyecto;
import com.practica1.rest.models.Proyecto;

public class ProyectoServices {
    private ProyectoDao obj;

    public Object[] listShowAll() throws Exception {
        if(!obj.getListAll().isEmpty()) {
            Proyecto[] lista = (Proyecto[]) obj.getListAll().toArray();
            Object[] respuesta = new Object[lista.length];
            for(int i = 0; i < lista.length; i++) {
                Inversionista in = new InversionistaServices().get(lista[i].getId_inversionista());
                Propuesta p = new PropuestaServices().get(lista[i].getId_propuesta());
                HashMap mapa = new HashMap<>();
                mapa.put("id", lista[i].getId());
                mapa.put("nombre", lista[i].getNombre());
                mapa.put("fechaInicio", lista[i].getFechaInicio());
                mapa.put("fechaFin", lista[i].getFechaFin());
                mapa.put("estado", lista[i].getEstado());
                mapa.put("inversionista", in);
                mapa.put("propuesta", p);
                respuesta[i] = mapa;
            }
            return respuesta;
        }
        return new Object[]{};
    }

    public ProyectoServices() {
        obj = new ProyectoDao();
    }
    
    public Boolean save() throws Exception {
        return obj.save();
    }

    public Boolean update() throws Exception {
        return obj.update();
    }

    public LinkedList listAll() {
        return obj.getListAll();
    }

    public Proyecto getProyecto() {
        return obj.getProyecto();
    }

    public void setProyecto(Proyecto proyecto) {
        obj.setProyecto(proyecto);
    }

    public Proyecto get(Integer id) throws Exception {
        return obj.get(id);
    }

    public Proyecto[] ordenarProyectos(String campoOrden, int direccion, int algoritmo) throws Exception {
        return obj.sortModels(campoOrden, direccion, algoritmo);
    }

}

