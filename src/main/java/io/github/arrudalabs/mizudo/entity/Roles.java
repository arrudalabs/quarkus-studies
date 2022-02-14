package io.github.arrudalabs.mizudo.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.stream.Stream;

@Entity
@Table(name = "roles")
public class Roles extends PanacheEntityBase implements Serializable {

    public static final String ADMIN = "ADMIN";
    public static final String USER = "USER";

    @Id
    @NotNull
    @Column(name = "usr", updatable = false)
    public String username;
    @Id
    @NotNull
    @Column(name = "role_name", updatable = false)
    @Enumerated(EnumType.STRING)
    public RoleName role;

    private static Roles create(User user, RoleName roleName) {
        var r = new Roles();
        r.username = user.username;
        r.role = roleName;
        return r;
    }

    public static void addRole(User user, RoleName roleName) {
        if (Roles.find("username = ?1 and role = ?2 ", user.username, roleName)
                .firstResultOptional().isEmpty()) {
            var newRole = Roles.create(user, roleName);
            newRole.persist();
        }
    }

    public static Stream<Roles> loadByUser(User user) {
        return Roles.find("username", user.username).stream();
    }

}
