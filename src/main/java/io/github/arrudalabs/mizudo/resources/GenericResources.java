package io.github.arrudalabs.mizudo.resources;

import io.github.arrudalabs.mizudo.entity.Usuario;
import org.eclipse.microprofile.jwt.JsonWebToken;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/resources")
@RequestScoped
public class GenericResources {

    @Inject
    JsonWebToken jwt;
    @RolesAllowed({Usuario.ADMINISTRADOR})
    @GET
    @Path("/admin-only")
    @Produces(APPLICATION_JSON)
    public Response user(
            @Context SecurityContext ctx
    ) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message", "Content for user " + ctx.getUserPrincipal().getName())
                        .build()).build();
    }

    @RolesAllowed({Usuario.COORDENADOR})
    @GET
    @Path("/coordenador")
    @Produces(APPLICATION_JSON)
    public Response coordenador(
            @Context SecurityContext ctx
    ) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message", "Content for user " + ctx.getUserPrincipal().getName())
                        .build()).build();
    }

    @RolesAllowed({Usuario.CONVIDADO})
    @GET
    @Path("/convidado")
    @Produces(APPLICATION_JSON)
    public Response convidado(
            @Context SecurityContext ctx
    ) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message", "Content for user " + ctx.getUserPrincipal().getName())
                        .build()).build();
    }

    @RolesAllowed({Usuario.EXAMINADOR})
    @GET
    @Path("/examinador")
    @Produces(APPLICATION_JSON)
    public Response examinador(
            @Context SecurityContext ctx
    ) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message", "Content for user " + ctx.getUserPrincipal().getName())
                        .build()).build();
    }

    @RolesAllowed({Usuario.INSTRUTOR})
    @GET
    @Path("/instrutor")
    @Produces(APPLICATION_JSON)
    public Response instrutor(
            @Context SecurityContext ctx
    ) {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message", "Content for user " + ctx.getUserPrincipal().getName())
                        .build()).build();
    }
}
