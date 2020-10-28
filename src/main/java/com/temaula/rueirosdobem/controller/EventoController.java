package com.temaula.rueirosdobem.controller;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.temaula.rueirosdobem.model.Evento;
import com.temaula.rueirosdobem.repository.EventoRepository;

@Path("/eventos")
@Transactional
public class EventoController {

	@Inject
	private EventoRepository repository;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response listarTodos() {
		return Response.ok(repository.listAll()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Evento pesquisaPorId(@PathParam("id") Long id) {
		Optional<Evento> evento = repository.findByIdOptional(id);
		return evento.orElseThrow(()-> new WebApplicationException(Response.Status.NOT_FOUND));
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Response inserir(Evento evento) {
		repository.persist(evento);
		return Response.created(URI.create(String.format("pessoas/%d", evento.getId() ))).build();
	}
}
