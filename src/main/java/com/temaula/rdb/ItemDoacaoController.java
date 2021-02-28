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

@Path("/itens")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
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
        return Response.status(Response.Status.CREATED).entity(itemDoacao).build();
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
    public Response atualizar(@PathParam("id") Long id, ItemDoacao itemDoacao) {
        ItemDoacao itemDoacaoNovo = repository.findById(id);
		if (itemDoacaoNovo == null){
			return Response.status(Response.Status.NOT_FOUND).entity("Item de doacao não existe").type(MediaType.TEXT_PLAIN).build();
		} else{
            
            
       // itemDoacaoNovo.setAutor(itemDoacao.getAutor());
       // itemDoacaoNovo.setCategoria(itemDoacao.getCategoria());
      //  itemDoacaoNovo.setDataCriacao(itemDoacao.getDataCriacao());
        itemDoacaoNovo.setNome(itemDoacao.getNome());
        itemDoacaoNovo.setValorReferencia(itemDoacao.getValorReferencia());
        itemDoacaoNovo.setAtivo(itemDoacao.getAtivo());
        
        repository.persist(itemDoacaoNovo);
   
        return Response.status(Response.Status.ACCEPTED).entity(itemDoacaoNovo).build();
    }}


}