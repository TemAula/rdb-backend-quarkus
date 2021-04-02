package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/itens")
public class RemoverItemResource {

    @DELETE
    @Path("{id}")
    @Transactional
    public Response removerItem(@PathParam("id") Long id) {
        Item.findByIdOptional(id)
            .map(Item.class::cast)
            .orElseThrow(() -> new NotFoundException("item com o id informado não encontrado"))
            .delete();
        return Response.noContent().build();
    }

}
