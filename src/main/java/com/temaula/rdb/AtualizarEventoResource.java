package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/eventos")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class AtualizarEventoResource {

    @PUT
    @Path("/{id}")
    public Response atualizarEvento(@PathParam("id") Long id,
                                    @Valid AtualizarEventoRequest request) {
        Optional<Evento> eventoLocalizado = Evento.findByIdOptional(id);
        Evento eventoRegistrado =
                eventoLocalizado.orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
        eventoRegistrado.atualizar(request);
        eventoRegistrado.persist();
        return Response.ok(new AtualizarEventoResponse(eventoRegistrado)).build();
    }

}
