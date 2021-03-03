package com.temaula.rdb.security;

import javax.inject.Inject;
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


    private final UsuarioService service;

    @Inject
    public UsuarioController(UsuarioService service) {
        this.service = service;
    }

    @POST
    public Response inserir(Usuario usuario) {
        service.criar(usuario);
       return Response.status(Response.Status.CREATED).entity(usuario).build();
    }

    public

    //cria usuario
    //edita usuario
    //atualiza usuario
    //remove usuario
    //exibe usuario
}
