package com.temaula.rdb;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/eventos/{eventoId}/itens")
@Produces({MediaType.APPLICATION_JSON})
public class ListarItemEventoResource {

    @GET
    public List<Item> listarItemEvento(
            @PathParam("eventoId") final Long eventoId
    ) {

        final var evento = Evento.findByIdOptional(eventoId)
                .map(Evento.class::cast)
                .orElseThrow(NotFoundException::new);

        return ItemEvento.streamByEvento(evento)
                .map(itemEvento -> itemEvento.item)
                .collect(Collectors.toList());
    }
}
