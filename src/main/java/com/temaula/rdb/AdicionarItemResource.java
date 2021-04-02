package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/itens")
@Consumes({ MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_JSON })
@Transactional
public class AdicionarItemResource {

    @POST
    public Response adicionar(@Valid Item item) {
        item.id = null; // ter certeza que irá criar um novo item
        item.persist();
        return Response.ok(item).build();
    }

}
