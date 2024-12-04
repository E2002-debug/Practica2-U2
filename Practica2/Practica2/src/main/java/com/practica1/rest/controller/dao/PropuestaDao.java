package com.practica1.rest.controller.dao;

import com.practica1.rest.controller.dao.implement.AdapterDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Propuesta;

public class PropuestaDao extends AdapterDao<Propuesta> {
    private Propuesta propuesta;
    private LinkedList listAll;

    public PropuestaDao() {
        super(Propuesta.class);
    }

    public Propuesta getPropuesta() {
        if (propuesta == null) {
            propuesta = new Propuesta();
        }
        return this.propuesta;
    }

    public void setPropuesta(Propuesta propuesta) {
        this.propuesta = propuesta;
    }
    
    public LinkedList getListAll() {
        if (listAll == null) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize() + 1;
        propuesta.setId(id);
        this.persist(this.propuesta);
        return true;
    }
    public Boolean update() throws Exception {
        this.merge(getPropuesta(), getPropuesta().getId());
        return true;
    }

}

