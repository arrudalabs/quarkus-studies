package io.github.arrudalabs.services;

import io.github.arrudalabs.entity.RoleName;
import io.github.arrudalabs.entity.User;
import io.github.arrudalabs.vo.Credentials;
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
    private final PasswordGeneratorService passwordGeneratorService;

    public InitServices(
            @ConfigProperty(name = "admin.password", defaultValue = "shoto") final String adminDefaultPassword,
            final PasswordGeneratorService passwordGeneratorService
    ) {
        this.adminDefaultPassword = adminDefaultPassword;
        this.passwordGeneratorService = passwordGeneratorService;
    }

    @Transactional
    public void onStart(@Observes StartupEvent event) {
        LOGGER.info("The application is starting...");
        User admin = User.findById("admin");
        if (admin == null) {
            LOGGER.info("it's missing admin user... creating this one...");
            User.createUser(
                    Credentials.of("admin", adminDefaultPassword),
                    Set.of(RoleName.ADM),
                    passwordGeneratorService);
        } else {
            LOGGER.info("changing admin password...");
            admin.forceChangePassword(adminDefaultPassword, passwordGeneratorService);
            admin.setRoleNames(Set.of(RoleName.ADM));
        }
    }
}
