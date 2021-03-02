package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/eventos")
public class EventoResource {

    @POST
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Transactional
    public Response adicionar(@Valid Evento evento) {
        evento.persist();
        return Response.ok(new EventoRegistrado(evento)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Transactional
    public Response atualizar(@PathParam("id") Long id,
                                    @Valid Evento evento) {
        Optional<Evento> eventoLocalizado = Evento.findByIdOptional(id);
        Evento eventoRegistrado =
                eventoLocalizado.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        eventoRegistrado.atualizar(evento);
        eventoRegistrado.persist();
        return Response.ok(new EventoRegistrado(eventoRegistrado)).build();
    }

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        return Response.ok(
                Evento.streamTodos()
                        .map(EventoRegistrado::new)
                        .collect(Collectors.toList()))
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response pesquisarId(@PathParam("id") Long id) {
        Optional<Evento> evento = Evento.findByIdOptional(id);
        final var eventoRegistrado = evento
                .map(EventoRegistrado::new)
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        return Response.ok(eventoRegistrado).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        Evento.deleteById(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

}
