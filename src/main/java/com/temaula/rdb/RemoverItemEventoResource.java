package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/eventos/{eventoId}/itens/{itemId}")
@Transactional
public class RemoverItemEventoResource {

    @DELETE
    public Response removerItemEvento(
            @PathParam("eventoId") final Long eventoId,
            @PathParam("itemId") final Long itemId
    ) {

        Evento evento = Evento
                .findByIdOptional(eventoId)
                .map(Evento.class::cast)
                .orElseThrow(NotFoundException::new);

        Item item = Item
                .findByIdOptional(itemId)
                .map(Item.class::cast)
                .orElseThrow(NotFoundException::new);

        ItemEvento.findByEventoAndItem(evento, item)
                .ifPresent(itemEvento -> itemEvento.delete());

        return Response.noContent().build();
    }

}
