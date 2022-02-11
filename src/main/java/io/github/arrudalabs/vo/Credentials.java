package io.github.arrudalabs.vo;

import org.hibernate.validator.constraints.Length;

public class Credentials {

    @Length(min = 1, max = 15)
    public String username;
    @Length(min = 5, max = 50)
    public String password;

    public static Credentials of(String username, String password) {
        var credentials = new Credentials();
        credentials.username = username;
        credentials.password = password;
        return credentials;
    }
}
