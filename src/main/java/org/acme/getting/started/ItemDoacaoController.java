package org.acme.getting.started;

import java.util.Optional;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.temaula.rueirosdobem.model.ItemDoacao;

@Path("/itens")
public class ItemDoacaoController {

    @Inject
    private ItemDoacaoRepository repository;

    @GET
    @Path("/")
    @Produces(value = MediaType.APPLICATION_JSON)
    public Response listarTodos() {
        return Response.ok(repository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(value = MediaType.APPLICATION_JSON)
    public ItemDoacao pesquisarId(@PathParam("id") Long id) {
        Optional<ItemDoacao> itemDoacao = repository.findByIdOptional(id);
        return itemDoacao
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public Response inserir(ItemDoacao itemDoacao) {
        repository.persist(itemDoacao);
        return Response.status(201).entity(itemDoacao).build();
    }

    @DELETE
    @Path("/{id}")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Transactional
    public Response deletar(@PathParam("id") Long id) {
        repository.deleteById(id);
        return Response.status(202).build();
    }

    @PUT
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Path("/{id}")
    @Transactional
    public Response atualizar(@PathParam("id") Long id, ItemDoacao itemDoacao) {
        itemDoacao.setId(id);

        Optional<ItemDoacao> itemDoacaoOp = repository.findByIdOptional(id);
		if (itemDoacaoOp.isEmpty()) {
			throw new NotFoundException("ItemDoacao não existe");
		}
        repository.persist(itemDoacao);
        
        return Response.status(202).entity(itemDoacao).build();
    }


}