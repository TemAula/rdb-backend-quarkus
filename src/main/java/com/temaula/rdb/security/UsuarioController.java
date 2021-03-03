package com.temaula.rdb.security;

import com.temaula.rdb.Pessoa;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/usuarios")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioController {


    @POST
    @Transactional
    public Response inserir(Usuario usuario) {
        return Response.status(Response.Status.CREATED).entity(pessoa).build();
    }

    //cria usuario
    //edita usuario
    //atualiza usuario
    //remove usuario
    //exibe usuario
}
