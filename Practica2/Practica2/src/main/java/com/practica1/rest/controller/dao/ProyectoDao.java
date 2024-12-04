package com.practica1.rest.controller.dao;
import java.util.function.ToIntBiFunction;
import com.practica1.rest.controller.dao.implement.AdapterDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Proyecto;
import com.practica1.rest.controller.tda.list.order.MergeSort;
import com.practica1.rest.controller.tda.list.order.QuickSort;
import com.practica1.rest.controller.tda.list.order.ShellSort;

public class ProyectoDao extends AdapterDao<Proyecto> {
    private Proyecto proyecto;
    private LinkedList<Proyecto> listAll;
    public ProyectoDao() {
        super(Proyecto.class);
    }
    public Proyecto getProyecto() {
        if (proyecto == null) {
            proyecto = new Proyecto();
        }
        return this.proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = (LinkedList<Proyecto>) listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        proyecto.setId(id);
        this.persist(this.proyecto);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getProyecto(), getProyecto().getId());
        return true;
    }

    public Proyecto[] sortModels(String campoOrden, int direccion, int algoritmo) {
        try {
            // Ordena la lista enlazada
            listAll = listAll.sortModels(listAll, campoOrden, direccion, algoritmo);
            // Convierte la lista ordenada a un array
            return listAll.toArray();
        } catch (Exception e) {
            System.err.println("Error al ordenar los proyectos: " + e.getMessage());
            return new Proyecto[0];
        }
    }



    
}


