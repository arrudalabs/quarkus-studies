package io.github.arrudalabs.security;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.enterprise.context.RequestScoped;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

@RequestScoped
public class PasswordEncoder {

    @ConfigProperty(name = "password.encoder.algorithm", defaultValue = "PBKDF2WithHmacSHA512")
    private String algorithm;
    @ConfigProperty(name = "password.encoder.iteration",defaultValue = "32")
    private Integer iteration;
    @ConfigProperty(name = "password.encoder.keylength", defaultValue = "256")
    private Integer keylength;

    /**
     * More info (https://www.owasp.org/index.php/Hashing_Java) 404 :(
     *
     * @param salt    salt
     * @param rawtext password
     * @return encoded password
     */
    public String encode(String salt, CharSequence rawtext) {
        try {
            byte[] result = SecretKeyFactory.getInstance(algorithm)
                    .generateSecret(
                            new PBEKeySpec(
                                    rawtext.toString().toCharArray(),
                                    salt.getBytes(),
                                    iteration,
                                    keylength)
                    ).getEncoded();
            return Base64.getEncoder().encodeToString(result);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            throw new RuntimeException(ex);
        }
    }

}
