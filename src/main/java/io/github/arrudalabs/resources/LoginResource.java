package io.github.arrudalabs.resources;

import io.github.arrudalabs.model.AuthResponse;
import io.github.arrudalabs.model.Credentials;
import io.github.arrudalabs.security.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Set;

@RequestScoped
@Path("/resource")
public class LoginResource {

    @Inject
    TokenUtils tokenUtils;

    @ConfigProperty(name = "jwt.duration")
    Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Path("/login")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(Credentials credentials) {
        String username = credentials.username;
        String password = users.get(username);
        if (password != null && password.equals(credentials.password)) {
            Set<String> roles = rolesByUser.get(username);
            try {
                String token = tokenUtils.generateToken(username, roles, duration, issuer);
                return Response.ok(new AuthResponse(token)).build();
            } catch (Exception e) {
                throw new WebApplicationException(e);
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    private static final Map<String, String> users = Map.of("admin", "1234", "user", "4321");

    private static final Map<String, Set<String>> rolesByUser = Map.of("admin", Set.of("ADMIN", "USER"), "user", Set.of("USER"));

}
