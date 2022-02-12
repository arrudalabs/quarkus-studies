package io.github.arrudalabs.entity;

import io.github.arrudalabs.security.PasswordGenerator;
import io.github.arrudalabs.vo.Credentials;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usrs")
public class User extends PanacheEntityBase {

    public static User createUser(Credentials credentials,
                                  Set<RoleName> roleNames,
                                  PasswordGenerator passwordGenerator) {
        var newUser = new User();
        newUser.username = credentials.username;
        newUser.salt = passwordGenerator.generateSalt().getBytes(StandardCharsets.UTF_8);
        newUser.hash = passwordGenerator.generatePassword(credentials.password, new String(newUser.salt, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        newUser.persist();
        newUser.setRoleNames(Optional.ofNullable(roleNames).orElse(Set.of(RoleName.USR)));
        return newUser;
    }

    public void setRoleNames(Set<RoleName> roleNames) {
        var thisRoles = getRoleNames();
        Optional.ofNullable(roleNames).orElse(new LinkedHashSet<>())
                .stream()
                .filter(r -> !thisRoles.contains(r))
                .forEach(r -> {
                    Roles.addRole(this, r);
                });
    }

    public Set<RoleName> getRoleNames() {
        return Roles.loadByUser(this).map(re -> re.role).collect(Collectors.toSet());
    }


    /**
     * Do not use it! It's only InitServices bootstrap
     *
     * @param newPassword
     * @param passwordGenerator
     */
    @Deprecated
    public void forceChangePassword(String newPassword, PasswordGenerator passwordGenerator) {
        this.hash = passwordGenerator.generatePassword(newPassword, new String(this.salt, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        this.persist();
    }

    public static Optional<User> authenticate(Credentials credentials,
                                              PasswordGenerator passwordGenerator) {

        User foundUser = User.findById(credentials.username);

        if (foundUser == null) {
            return Optional.empty();
        }

        String generatePassword = passwordGenerator.generatePassword(credentials.password, new String(foundUser.salt, StandardCharsets.UTF_8));

        if (!new String(foundUser.hash, StandardCharsets.UTF_8).equals(generatePassword)) {
            return Optional.empty();
        }

        return Optional.of(foundUser);

    }

    @Id
    @Size(min = 5, max = 15)
    @Column(name = "usr")
    public String username;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "seed")
    private byte[] salt;

    @NotEmpty
    @Basic(optional = false)
    @Column(name = "pass")
    private byte[] hash;

}
