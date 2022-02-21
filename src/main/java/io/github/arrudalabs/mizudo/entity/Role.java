package io.github.arrudalabs.mizudo.entity;

public enum Role {
    Administrador,
    Instrutor,
    Coordenador,
    Examinador,
    Convidado;

    public String fullName() {
        return Usuario.nomeRole(this);
    }
}
