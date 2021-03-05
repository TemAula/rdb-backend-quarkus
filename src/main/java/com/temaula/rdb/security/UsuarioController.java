package com.temaula.rdb.security;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import java.security.Principal;

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
    public Response inserir(UsuarioDTO usuario) {
       return Response.status(Response.Status.CREATED)
               .entity(service.criar(usuario)).build();
    }

    @PUT
    @Path("{id}")
    @RolesAllowed({"user", "admin"})
    public UsuarioDTO atualizar(@PathParam("id") Long id, UsuarioDTO usuario,
                                @Context SecurityContext securityContext) {
        Principal principal = securityContext.getUserPrincipal();
        return service.atualizar(id, usuario, principal);
    }

    @GET
    @RolesAllowed({"user", "admin"})
    @Path("/me")
    public UsuarioDTO me(@Context SecurityContext securityContext) {
        return service.me(securityContext.getUserPrincipal())
                .orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
    }

}
