package com.temaula.rdb;

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
import java.util.Optional;

@Path("/pessoas")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PessoaController {

    @Inject
    private PessoaRepository repository;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Pessoa pesquisarId(@PathParam("id") Long id) {
        Optional<Pessoa> pessoa = repository.findByIdOptional(id);
        return pessoa
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response inserir(Pessoa pessoa) {
        repository.persist(pessoa);
        return Response.status(Response.Status.CREATED).entity(pessoa).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        repository.deleteById(id);
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, Pessoa pessoa) {
        Pessoa pessoaNova = repository.findById(id);
		if (pessoaNova == null){
			return Response.status(Response.Status.NOT_FOUND).entity("Pessoa não existe").type(MediaType.TEXT_PLAIN).build();
		} else{

        pessoaNova.setSenha(pessoa.getSenha());
        pessoaNova.setNome(pessoa.getNome());
        pessoaNova.setTelefone(pessoa.getTelefone());
        pessoaNova.setEmail(pessoa.getEmail());
        
        repository.persist(pessoaNova);
   
        return Response.status(Response.Status.ACCEPTED).entity(pessoaNova).build();
    }}





}