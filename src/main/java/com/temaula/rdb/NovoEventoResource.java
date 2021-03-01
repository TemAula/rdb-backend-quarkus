package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/eventos")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class NovoEventoResource {

    @POST
    public Response novoEvento(@Valid NovoEventoRequest novoEvento) {
        Evento eventoRegistrado = Evento.novoEvento(novoEvento);
        return Response.ok(new NovoEventoResponse(eventoRegistrado)).build();
    }

}
