package io.github.arrudalabs.resources;

import io.github.arrudalabs.entity.RoleName;
import io.github.arrudalabs.entity.User;
import io.github.arrudalabs.services.PasswordGeneratorService;
import io.github.arrudalabs.vo.AuthResponse;
import io.github.arrudalabs.vo.Credentials;
import io.github.arrudalabs.security.TokenUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
@Path("/resource")
public class AuthenticationResource {

    @Inject
    TokenUtils tokenUtils;

    @ConfigProperty(name = "jwt.duration")
    Long duration;
    @ConfigProperty(name = "mp.jwt.verify.issuer")
    String issuer;

    @Inject
    PasswordGeneratorService passwordGeneratorService;

    @Path("/login")
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    public Response login(@Valid Credentials credentials) {
        Optional<User> authenticatedUser = User.authenticate(credentials, passwordGeneratorService);
        if (authenticatedUser.isPresent()) {
            Set<String> roles = authenticatedUser.get()
                    .getRoleNames().stream().map(RoleName::fullName).collect(Collectors.toSet());;
            try {
                String token = tokenUtils.generateToken(authenticatedUser.get().username, roles, duration, issuer);
                return Response.ok(new AuthResponse(token)).build();
            } catch (Exception e) {
                throw new WebApplicationException(e);
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

}
