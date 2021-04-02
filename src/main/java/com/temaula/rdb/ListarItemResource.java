package com.temaula.rdb;

import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/itens")
@Produces({ MediaType.APPLICATION_JSON })
public class ListarItemResource {

    @GET
    public List<Item> listAll(){
        return Item.listAll();
    }

}
