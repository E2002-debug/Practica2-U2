package com.practica1.rest.controller.dao;
import java.util.function.ToIntBiFunction;
import com.practica1.rest.controller.dao.implement.AdapterDao;
import com.practica1.rest.controller.tda.list.LinkedList;
import com.practica1.rest.models.Inversionista;

public class InversionistaDao extends AdapterDao<Inversionista> {
    
    private Inversionista inversionista;
    private LinkedList listAll;
    public InversionistaDao() {
        super(Inversionista.class);
    }
    public Inversionista getInversionista() {
        if (inversionista == null) {
            inversionista = new Inversionista();
        }
        return this.inversionista;
    }

    public void setInversionista(Inversionista inversionista) {
        this.inversionista = inversionista;
    }
    
    public LinkedList getListAll() {
        if(listAll == null){
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean save() throws Exception {
        Integer id = getListAll().getSize()+1;
        inversionista.setId(id);
        this.persist(this.inversionista);
        return true;
    }

    public Boolean update() throws Exception {
        this.merge(getInversionista(), getInversionista().getId());
        return true;
    }



}


