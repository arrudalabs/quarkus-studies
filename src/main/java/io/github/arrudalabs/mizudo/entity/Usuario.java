package io.github.arrudalabs.mizudo.entity;

import io.github.arrudalabs.mizudo.security.PasswordGenerator;
import io.github.arrudalabs.mizudo.vo.Credentials;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "membros")
public class Usuario extends PanacheEntityBase {

    public static final String ADMINISTRADOR = "ADMINISTRADOR";
    public static final String CONVIDADO = "CONVIDADO";
    public static final String INSTRUTOR = "INSTRUTOR";
    public static final String EXAMINADOR = "EXAMINADOR";
    public static final String COORDENADOR = "COORDENADOR";
    public static final String ANONIMO = "ANONIMO";

    public static Usuario defineUsuario(Membro membro,
                                        Credentials credentials,
                                        Set<Role> roleNames,
                                        PasswordGenerator passwordGenerator) {

        Usuario newUser = Usuario.findById(membro.matricula);

        newUser.username = credentials.username;

        // definindo a senha de acesso
        newUser.salt = passwordGenerator.generateSalt().getBytes(StandardCharsets.UTF_8);
        newUser.hash = passwordGenerator.generatePassword(credentials.password, new String(newUser.salt, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        newUser.persist();

        // definindo os papeis
        newUser.defineRoles(Optional.ofNullable(roleNames).orElse(Set.of()));
        return newUser;
    }


    public static Optional<Usuario> autentique(Credentials credentials,
                                               PasswordGenerator passwordGenerator) {

        Usuario foundUser = Usuario.buscarPorUsername(credentials.username);

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
    @Column(insertable = false)
    public Long matricula;

    @Size(min = 4, max = 15)
    @Column(name = "username")
    public String username;

    @Basic(optional = true)
    @Column(name = "seed")
    private byte[] salt;

    @Basic(optional = true)
    @Column(name = "hash")
    private byte[] hash;

    @Column(name = "eh_admin")
    private Integer ehAdmin;

    @Column(name = "eh_instrutor")
    private Integer ehInstrutor;

    @Column(name = "eh_coordenador")
    private Integer ehCoordenador;

    @Column(name = "eh_examinador")
    private Integer ehExaminador;

    @Column(name = "eh_convidado")
    private Integer ehConvidado;

    public static String nomeRole(Role roleName) {

        switch (roleName) {
            case Administrador:
                return ADMINISTRADOR;
            case Convidado:
                return CONVIDADO;
            case Instrutor:
                return INSTRUTOR;
            case Examinador:
                return EXAMINADOR;
            case Coordenador:
                return COORDENADOR;
            default:
                return ANONIMO;
        }
    }

    public static Usuario buscarPorUsername(String username) {
        return Usuario.find("username = ?1 ", username).firstResult();
    }

    public void defineRoles(Set<Role> roleNames) {
        if (roleNames == null) return;
        roleNames.forEach(roleName -> {
            switch (roleName) {
                case Administrador:
                    this.ehAdmin = 1;
                    break;
                case Convidado:
                    this.ehConvidado = 1;
                    break;
                case Instrutor:
                    this.ehInstrutor = 1;
                    break;
                case Examinador:
                    this.ehExaminador = 1;
                    break;
                case Coordenador:
                    this.ehCoordenador = 1;
                    break;
                default:
            }
        });
        this.persist();
    }

    public Set<Role> roles() {
        var roles = Set.of(Role.values())
                .stream().filter(role -> {
                    return this.detem(role);
                }).collect(Collectors.toSet());
        return roles;
    }

    private boolean detem(Role roleName) {
        if (roleName == null) return false;
        switch (roleName) {
            case Instrutor:
                return 1 == Optional.ofNullable(this.ehAdmin).orElse(0) || 1 == Optional.ofNullable(this.ehInstrutor).orElse(0);
            case Coordenador:
                return 1 == Optional.ofNullable(this.ehAdmin).orElse(0) || 1 == Optional.ofNullable(this.ehCoordenador).orElse(0);
            case Convidado:
                return 1 == Optional.ofNullable(this.ehAdmin).orElse(0) || 1 == Optional.ofNullable(this.ehConvidado).orElse(0);
            case Examinador:
                return 1 == Optional.ofNullable(this.ehAdmin).orElse(0) || 1 == Optional.ofNullable(this.ehExaminador).orElse(0);
            default:
                return 1 == Optional.ofNullable(this.ehAdmin).orElse(0);
        }
    }


    /**
     * Do not use it! It's only InitServices bootstrap
     *
     * @param newPassword
     * @param passwordGenerator
     */
    @Deprecated
    public void forceAtualizacaoDeSenha(String newPassword, PasswordGenerator passwordGenerator) {
        this.hash = passwordGenerator.generatePassword(newPassword, new String(this.salt, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
        this.persist();
    }
}
