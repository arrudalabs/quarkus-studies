package io.github.arrudalabs.services;

import io.github.arrudalabs.security.PasswordGenerator;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import javax.inject.Inject;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class PasswordGeneratorServiceTest {

    @Inject
    PasswordGenerator passwordGenerator;

    @Test
    public void testGenerateSalt() {
        final var saltSet = IntStream.range(0, 5)
                .boxed()
                .map(v -> passwordGenerator.generateSalt()).collect(Collectors.toSet());
        assertEquals(
                5,
                saltSet.size(),
                "must generate a different salt for each call"
        );
    }

    @ParameterizedTest
    @CsvSource({
            "MuCsXo3r7oD2GExs6cGhtg==,mypassword1,SPO6ctO71Ec0IN8XynYDy1SL6wP9cg1B1X8Wlm7B9tnS30N07s1koSoSNhwt55L0pt42O0g0PjfZkli1GfW7+mcoskYViepU779UK80ukCon6G3hW5zepKr6lEzWWQVaa97qY1WRJ/VvZiTRgvbQxcimcU4N8UXc9AsAHqOR2NQ=",
            "ppC8PwPuzmDhDkdFbsre3A==,mypassword2,gArvjhixQGat92hGBe5rJfnWyuXzT/5KNdSmxot9wASc0T+3GJ9SVbwRYpuf3avYpLqjRO1h6FTvdV+sHallVzQKTMyFUCB2vAQcJphvbeTZQzPkhfOrGwRgi8i3SGZf2d6yEriZvX3xpPGIfx4WvXsFXyPCJCI2C67c8f04TMQ=",
            "nlvIVO2c4HS+zl0yRYCj/Q==,mypassword3,WP7fwkUwG0ALp/lAN8pLY/prkO5l1Aq/ctmyu5tH/B9vTL/0F/rOj3/cZxCXWlG6NL1n4ecH8cKMcQLkf1OrkCbm3w/b6977kc1EoLuN7Q6Y5bFvSSGpnldkIuAZuZ3A6Cg43xYfRR9bXany1ZEg6GrLhp8gfgLNkxslOWIv1bo=",
    })
    public void testGeneratePassword(
            final String salt,
            final String plainTextPassword,
            final String expectedPassword
    ) {
        var encryptedPasswd = passwordGenerator.generatePassword(plainTextPassword, salt);
        assertEquals(expectedPassword, encryptedPasswd);
    }
}