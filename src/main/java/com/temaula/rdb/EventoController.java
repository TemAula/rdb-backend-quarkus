package com.temaula.rdb;

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

    @PUT
    @Path("/{id}")
    @Consumes(value = MediaType.APPLICATION_JSON)
    @Produces(value = MediaType.APPLICATION_JSON)
    @Transactional
    public Response atualizar(@PathParam("id") Long id, @Valid Evento evento) {
        Evento eventoRegistrado = Evento.findById(id);
		eventoRegistrado.setAtivo(evento.isAtivo());
        eventoRegistrado.setPeriodo(evento.getDataInicio(),evento.getDataFim());
        eventoRegistrado.setNome(evento.getNome());
        eventoRegistrado.setDescricao(evento.getDescricao());
//		eventoRegistrado.setUrlImagem(evento.getUrlImagem());
        eventoRegistrado.persist();
        return Response.status(Response.Status.ACCEPTED).entity(eventoRegistrado).build();
    }
}
