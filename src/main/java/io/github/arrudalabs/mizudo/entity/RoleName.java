package io.github.arrudalabs.mizudo.entity;

public enum RoleName {

    ADM,
    USR;

    public String fullName() {
        switch (this) {
            case ADM:
                return Roles.ADMIN;
            default:
                return Roles.USER;
        }
    }
}
