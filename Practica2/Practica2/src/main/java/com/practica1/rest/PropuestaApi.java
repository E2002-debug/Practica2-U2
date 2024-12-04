package com.practica1.rest;

import java.util.HashMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.practica1.rest.controller.dao.services.InversionistaServices;
import com.practica1.rest.controller.dao.services.PropuestaServices;

@Path("propuesta")
public class PropuestaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilys() {
        HashMap<String, Object> map = new HashMap<>();
        PropuestaServices ps = new PropuestaServices();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {

        PropuestaServices ps = new PropuestaServices();
        ps.getPropuesta().setDescripcion(map.get("descripcion").toString());
        ps.getPropuesta().setMonto(Float.parseFloat(map.get("monto").toString()));
        ps.getPropuesta().setWatsGenerados(Float.parseFloat(map.get("watsGenerados").toString()));

        HashMap<String, Object> res = new HashMap<>();
        try {
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Propuesta registrada correctamente");
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
        return Response.ok(res).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPropuesta(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        PropuestaServices ps = new PropuestaServices();
        try {
            ps.setPropuesta(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }
       
        map.put("msg", "Ok");
        map.put("data", ps.getPropuesta());
        if (ps.getPropuesta().getId() == null) {
            map.put("data", "Inversionista no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }
    

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        PropuestaServices ps = new PropuestaServices();
        HashMap<String, Object> res = new HashMap<>();

        try {
            // Obtener la propuesta y actualizar sus datos
            ps.setPropuesta(ps.get(Integer.parseInt(map.get("id").toString())));
            ps.getPropuesta().setDescripcion(map.get("descripcion").toString());
            ps.getPropuesta().setMonto(Float.parseFloat(map.get("monto").toString()));
            ps.getPropuesta().setWatsGenerados(Float.parseFloat(map.get("watsGenerados").toString()));

            // Actualizando propuesta
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Propuesta actualizada correctamente");
            return Response.ok(res).build();

        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Formato de número inválido: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error al actualizar la propuesta: " + e.getMessage());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
