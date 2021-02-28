package com.temaula.rdb;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/eventos")
public class EventoController {

	@Inject
	private EventoRepsitory repository;


	@GET
	@Path("/")
	@Produces( value = MediaType.APPLICATION_JSON )
	public Response listarTodos() {
		return Response.ok(repository.listAll()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(value = MediaType.APPLICATION_JSON)
	public Evento pesquisarId(@PathParam("id") Long id) {
		Optional<Evento> evento = repository.findByIdOptional(id);
		return evento.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
	}
	
	@POST
	@Path("/")
	@Produces( value = MediaType.APPLICATION_JSON )
	@Consumes( value = MediaType.APPLICATION_JSON )
	@Transactional
	public Response inserir(Evento evento) {
		repository.persist(evento);
		return Response.created(URI.create(String.format("/eventos/%d", evento.getId()))).build();
	}
	
	@DELETE
	@Path("/{id}")
	@Consumes(value  = MediaType.APPLICATION_JSON)
	@Transactional
	public Response deletar(@PathParam("id") Long id) {
		repository.deleteById(id);
		return Response.status(Response.Status.ACCEPTED).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes( value = MediaType.APPLICATION_JSON )
	@Produces( value = MediaType.APPLICATION_JSON )
	@Transactional
	public Response atualizar(@PathParam("id") Long id, Evento evento) {
		Evento eventoNovo = repository.findById(id);
		eventoNovo.setAtivo(evento.isAtivo());
		eventoNovo.setDataCriacao(evento.getDataCriacao());
		eventoNovo.setDataInicio(evento.getDataInicio());
		eventoNovo.setDataFim(evento.getDataFim());
		eventoNovo.setNome(evento.getNome());
		eventoNovo.setDescricao(evento.getDescricao());
		eventoNovo.setUrlImagem(evento.getUrlImagem());
		
		repository.persist(eventoNovo);
		return Response.status(Response.Status.ACCEPTED).entity(eventoNovo).build();
	}
}
