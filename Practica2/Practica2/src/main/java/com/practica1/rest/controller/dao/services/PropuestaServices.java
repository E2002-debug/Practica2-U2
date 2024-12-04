package com.practica1.rest.controller.dao.services;
import com.practica1.rest.controller.dao.PropuestaDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Propuesta;

public class PropuestaServices {
    private PropuestaDao obj;

    public PropuestaServices() {
        obj = new PropuestaDao();
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

    public Propuesta getPropuesta() {
        return obj.getPropuesta();
    }

    public void setPropuesta(Propuesta propuesta) {
        obj.setPropuesta(propuesta);
    }

    public Propuesta get(Integer id) throws Exception {
        return obj.get(id);
    }
}

