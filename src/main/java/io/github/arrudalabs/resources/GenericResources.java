package io.github.arrudalabs.resources;

import io.github.arrudalabs.entity.RoleName;
import io.github.arrudalabs.entity.Roles;
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

    @RolesAllowed({Roles.ADMIN})
    @GET
    @Path("/user")
    @Produces(APPLICATION_JSON)
    public Response user() {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message","Content for user")
                        .build()).build();
    }

    @RolesAllowed("ADMIN")
    @GET
    @Path("/admin")
    @Produces(APPLICATION_JSON)
    public Response admin() {
        return Response.ok(
                Json.createObjectBuilder()
                        .add("message","Content for admin")
                        .build()).build();
    }

    @RolesAllowed({"USER", "ADMIN"})
    @GET
    @Path("/user-or-admin")
    @Produces(APPLICATION_JSON)
    public Response userOrAdmin(@Context SecurityContext ctx) {

        return Response.ok(
                Json.createObjectBuilder()
                        .add("message","Content for user and admin - ")
                        .build()).build();
    }
}
