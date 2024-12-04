package com.practica1.rest;

import java.util.HashMap;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import com.practica1.rest.controller.dao.services.InversionistaServices;

@Path("inversionista")
public class InversionistaApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFamilys() {
        HashMap<String, Object> map = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();

        // Medición de tiempo de ejecución
        long startTime = System.currentTimeMillis();

        // Obtener la lista de inversionistas
        Object[] data = ps.listAll().toArray();

        // Comprobar si la lista está vacía
        if (data.length == 0) {
            map.put("data", new Object[] {});
        } else {
            map.put("data", data);
        }

        // Tiempo de ejecución final
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; // Duración en milisegundos

        // Agregar información sobre el tiempo de ejecución al mapa
        map.put("msg", "Ok");
        map.put("executionTime", duration + " ms");

        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        InversionistaServices ps = new InversionistaServices();
        ps.getInversionista().setNombre(map.get("nombre").toString());
        ps.getInversionista().setApellido(map.get("apellido").toString());
        ps.getInversionista().setDni(map.get("dni").toString());
        ps.getInversionista().setMontoI(Float.parseFloat(map.get("montoI").toString()));

        HashMap<String, Object> res = new HashMap<>();
        try {
            ps.save();
            res.put("msg", "OK");
            res.put("data", "Inversionista registrado correctamente");
            return Response.ok(res).build();
        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllInversionista(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();
        try {
            ps.setInversionista(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("msg", "Ok");
        map.put("data", ps.getInversionista());
        if (ps.getInversionista().getId() == null) {
            map.put("data", "Inversionista no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        InversionistaServices ps = new InversionistaServices();

        try {
            // Validación de parámetros
            if (!map.containsKey("id") || !map.containsKey("nombre") || !map.containsKey("apellido") || !map.containsKey("dni")
                    || !map.containsKey("montoI")) {
                res.put("msg", "Error");
                res.put("data", "Faltan parámetros requeridos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Intenta obtener y validar el ID
            Integer id = Integer.parseInt(map.get("id").toString());
            ps.setInversionista(ps.get(id));

            // Verifica si el inversionista existe
            if (ps.getInversionista() == null) {
                res.put("msg", "Error");
                res.put("data", "Inversionista no encontrado");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }

            // Actualiza los datos del inversionista
            ps.getInversionista().setNombre(map.get("nombre").toString());
            ps.getInversionista().setApellido(map.get("apellido").toString());
            ps.getInversionista().setDni(map.get("dni").toString());
            ps.getInversionista().setMontoI(Float.parseFloat(map.get("montoI").toString()));

            // Llama al método de actualización del servicio
            ps.update();
            res.put("msg", "OK");
            res.put("data", "Inversionista actualizado correctamente");
            return Response.ok(res).build();

        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Formato de número inválido: " + e.getMessage());
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error al actualizar el inversionista: " + e.getMessage());
            // Registro del error en el log
            System.err.println("Error en la actualización: " + e);
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

}
