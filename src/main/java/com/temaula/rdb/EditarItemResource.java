package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/itens")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Transactional
public class EditarItemResource {

    @PUT
    @Path("{id}")
    public Response editar(@PathParam("id") Long id, @Valid Item item) {
        Item itemRegistrado = Item.findByIdOptional(id)
            .map(Item.class::cast)
            .orElseThrow(NotFoundException::new);
        itemRegistrado.editar(item);
        return Response.ok(itemRegistrado).build();
    }
}
