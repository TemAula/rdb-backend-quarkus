package com.temaula.rdb;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/itens")
@Produces({ MediaType.APPLICATION_JSON })
public class CarregarItemResource {

    @GET
    @Path("{id}")
    public Item carregarItem(@PathParam("id") Long id) {
        return Item.findByIdOptional(id)
            .map(Item.class::cast)
            .orElseThrow(() -> new NotFoundException("item com o id informado não encontrado"));
    }

}
