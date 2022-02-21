package io.github.arrudalabs.mizudo.services;

import io.github.arrudalabs.mizudo.entity.Membro;
import io.github.arrudalabs.mizudo.entity.Role;
import io.github.arrudalabs.mizudo.entity.Usuario;
import io.github.arrudalabs.mizudo.security.PasswordGenerator;
import io.github.arrudalabs.mizudo.vo.Credentials;
import io.quarkus.runtime.StartupEvent;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.transaction.Transactional;
import java.util.Set;
import java.util.logging.Logger;

@ApplicationScoped
public class InitServices {

    private static final Logger LOGGER = Logger.getLogger("InitServices");

    private String adminDefaultPassword;
    private final PasswordGenerator passwordGenerator;

    public InitServices(
            @ConfigProperty(name = "admin.password", defaultValue = "shoto") final String adminDefaultPassword,
            final PasswordGenerator passwordGenerator
    ) {
        this.adminDefaultPassword = adminDefaultPassword;
        this.passwordGenerator = passwordGenerator;
    }

    @Transactional
    public void onStart(@Observes StartupEvent event) {
        LOGGER.info("The application is starting...");
        Usuario adminUser = Usuario.buscarPorUsername("admin");
        if (adminUser == null) {
            LOGGER.info("it's missing adminUser user... creating this one...");
            Membro membroAdmin = Membro.criarMembro("Administrador do sistema","admin");
            Usuario.defineUsuario(
                    membroAdmin,
                    Credentials.of("admin", adminDefaultPassword),
                    Set.of(Role.Administrador),
                    passwordGenerator);
        } else {
            LOGGER.info("changing adminUser password...");
            adminUser.forceAtualizacaoDeSenha(adminDefaultPassword, passwordGenerator);
            adminUser.defineRoles(Set.of(Role.Administrador));
        }

        Usuario dummyUser = Usuario.buscarPorUsername("user");
        if (dummyUser == null) {
            LOGGER.info("it's missing dummy user... creating this one...");
            Membro membroAdmin = Membro.criarMembro("Usuário Simples","user");
            Usuario.defineUsuario(
                    membroAdmin,
                    Credentials.of("user", adminDefaultPassword),
                    Set.of(Role.Convidado),
                    passwordGenerator);
        }
    }
}
