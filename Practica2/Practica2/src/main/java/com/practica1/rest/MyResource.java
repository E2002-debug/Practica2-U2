package com.practica1.rest;

import java.util.HashMap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.practica1.rest.controller.dao.InversionistaDao;

@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        HashMap<String, Object> mapa = new HashMap<>();
        InversionistaDao in = new InversionistaDao();
        String aux = "";

        // Medición de tiempo de ejecución
        long startTime = System.currentTimeMillis();

        try {
            in.getInversionista().setNombre("Juan");
            in.getInversionista().setApellido("Perez");
            in.getInversionista().setMontoI(1000f);
            in.save();

            aux = "La lista está vacía: " + in.listAll().isEmpty();
        } catch (Exception e) {
            System.out.println("Errores: " + e.getCause());
            mapa.put("msg", "Error");
            mapa.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        // Tiempo de ejecución final
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; // Duración en milisegundos

        mapa.put("msg", "Ok");
        mapa.put("data", "test " + aux + " | Tiempo de ejecución: " + duration + " ms");
        
        return Response.ok(mapa).build();
    }
}


