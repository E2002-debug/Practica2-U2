package com.practica1.rest;

import java.util.HashMap;
import java.util.LinkedList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.practica1.rest.controller.dao.ProyectoDao;
import com.practica1.rest.controller.dao.services.InversionistaServices;
import com.practica1.rest.controller.dao.services.PropuestaServices;
import com.practica1.rest.controller.dao.services.ProyectoServices;
import com.practica1.rest.models.Inversionista;
import com.practica1.rest.models.Propuesta;
import com.practica1.rest.models.Proyecto;

@Path("proyecto")
public class ProyectoApi {

    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        HashMap map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        map.put("msg", "OK");
        // TODO
        // map.put("data", ps.listAll().toArray());
        try {
            map.put("data", ps.listShowAll());
            if (ps.listAll().isEmpty()) {
                map.put("data", new Object[] {});
            }

        } catch (Exception e) {
            map.put("data", new Object[] {});
            System.out.println("Error " + e);
            // TODO: handle exception
        }
        return Response.ok(map).build();
    }

    @Path("/save")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response save(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Validar que los datos requeridos estén presentes
            if (map.get("inversionista") != null && map.get("propuesta") != null) {

                // Obtener inversionista y propuesta
                InversionistaServices inversionistaServices = new InversionistaServices();
                inversionistaServices
                        .setInversionista(
                                inversionistaServices.get(Integer.parseInt(map.get("inversionista").toString())));

                PropuestaServices propuestaServices = new PropuestaServices();
                propuestaServices
                        .setPropuesta(propuestaServices.get(Integer.parseInt(map.get("propuesta").toString())));

                // Validar que existan el inversionista y la propuesta
                if (inversionistaServices.getInversionista().getId() != null
                        && propuestaServices.getPropuesta().getId() != null) {

                    // Crear el proyecto
                    ProyectoServices ps = new ProyectoServices();
                    ps.getProyecto().setNombre(map.get("nombre").toString());
                    ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
                    ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
                    ps.getProyecto().setEstado(map.get("estado").toString());
                    ps.getProyecto().setId_inversionista(inversionistaServices.getInversionista().getId());
                    ps.getProyecto().setId_propuesta(propuestaServices.getPropuesta().getId());
                    ps.save();

                    // Respuesta de éxito
                    res.put("msg", "OK");
                    res.put("data", "Proyecto registrado correctamente");
                    return Response.ok(res).build();
                } else {
                    res.put("msg", "Error");
                    res.put("data", "El inversionista o la propuesta no existen");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                }
            } else {
                // Datos insuficientes
                res.put("msg", "Error");
                res.put("data", "Faltan datos requeridos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            // Manejo de errores generales
            System.out.println("Error en save data: " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllProyecto(@PathParam("id") Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        ProyectoServices ps = new ProyectoServices();
        try {
            ps.setProyecto(ps.get(id));
        } catch (Exception e) {
            // TODO: handle exception
        }

        map.put("msg", "Ok");
        map.put("data", ps.getProyecto());
        if (ps.getProyecto().getId() == null) {
            map.put("data", "Inversionista no encontrado");
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Validar que los datos requeridos estén presentes
            if (map.get("id") != null && map.get("inversionista") != null && map.get("propuesta") != null) {

                // Obtener el proyecto existente
                ProyectoServices ps = new ProyectoServices();
                ps.setProyecto(ps.get(Integer.parseInt(map.get("id").toString())));

                if (ps.getProyecto().getId() == null) {
                    // Proyecto no encontrado
                    res.put("msg", "Error");
                    res.put("data", "El proyecto no existe");
                    return Response.status(Status.BAD_REQUEST).entity(res).build();
                } else {
                    // Obtener inversionista y propuesta
                    InversionistaServices inversionistaServices = new InversionistaServices();
                    inversionistaServices.setInversionista(
                            inversionistaServices.get(Integer.parseInt(map.get("inversionista").toString())));

                    PropuestaServices propuestaServices = new PropuestaServices();
                    propuestaServices.setPropuesta(
                            propuestaServices.get(Integer.parseInt(map.get("propuesta").toString())));

                    // Validar que existan el inversionista y la propuesta
                    if (inversionistaServices.getInversionista().getId() != null
                            && propuestaServices.getPropuesta().getId() != null) {

                        // Actualizar el proyecto
                        ps.getProyecto().setNombre(map.get("nombre").toString());
                        ps.getProyecto().setFechaInicio(map.get("fechaInicio").toString());
                        ps.getProyecto().setFechaFin(map.get("fechaFin").toString());
                        ps.getProyecto().setEstado(map.get("estado").toString());
                        ps.getProyecto().setId_inversionista(inversionistaServices.getInversionista().getId());
                        ps.getProyecto().setId_propuesta(propuestaServices.getPropuesta().getId());
                        ps.update();

                        // Respuesta de éxito
                        res.put("msg", "OK");
                        res.put("data", "Proyecto actualizado correctamente");
                        return Response.ok(res).build();
                    } else {
                        res.put("msg", "Error");
                        res.put("data", "El inversionista o la propuesta no existen");
                        return Response.status(Status.BAD_REQUEST).entity(res).build();
                    }
                }
            } else {
                // Datos insuficientes
                res.put("msg", "Error");
                res.put("data", "Faltan datos requeridos");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            // Manejo de errores generales
            System.out.println("Error en update data: " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    @Path("/sort")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sortProjects(HashMap<String, Object> params) {
        HashMap<String, Object> res = new HashMap<>();
        try {
            // Validar que los parámetros requeridos estén presentes
            if (params.get("campoOrden") != null && params.get("direccion") != null
                    && params.get("algoritmo") != null) {
                String campoOrden = params.get("campoOrden").toString(); // Atributo para ordenar
                int direccion = Integer.parseInt(params.get("direccion").toString()); // 1 = ascendente, 2 = descendente
                int algoritmo = Integer.parseInt(params.get("algoritmo").toString()); // Algoritmo: 1 = QuickSort, 2 =
                                                                                      // MergeSort, 3 = ShellSort

                // Llamar al servicio para ordenar los proyectos
                ProyectoServices ps = new ProyectoServices();
                Proyecto[] sortedProjects = ps.ordenarProyectos(campoOrden, direccion, algoritmo);

                res.put("msg", "OK");
                res.put("data", sortedProjects);
                return Response.ok(res).build();
            } else {
                // Parámetros insuficientes
                res.put("msg", "Error");
                res.put("data", "Faltan parámetros requeridos: campoOrden, direccion, algoritmo");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }
        } catch (Exception e) {
            // Manejo de errores generales
            System.err.println("Error al ordenar proyectos: " + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }
}
