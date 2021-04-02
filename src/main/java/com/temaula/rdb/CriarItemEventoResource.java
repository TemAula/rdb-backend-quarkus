package com.temaula.rdb;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/eventos/{eventoId}/itens")
@Consumes({MediaType.APPLICATION_JSON})
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public class CriarItemEventoResource {

    @POST
    public Item criarItemEvento(
            @PathParam("eventoId")
            final Long eventoId,
            final Item item) {

        Evento evento=Evento.findByIdOptional(eventoId)
                .map(Evento.class::cast)
                .orElseThrow(NotFoundException::new);

        Item itemParaRelacionar=Item.findByIdOptional(item.id)
                .map(Item.class::cast)
                .orElseThrow(NotFoundException::new);

        return ItemEvento.create(evento,itemParaRelacionar).item;

    }


}
