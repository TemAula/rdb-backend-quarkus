package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/eventos")
public class EventoController {

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        return Response.ok(Evento.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Evento pesquisarId(@PathParam("id") Long id) {
        Optional<Evento> evento = Evento.findByIdOptional(id);
        return evento.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @DELETE
    @Path("/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Evento.deleteById(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
