package io.github.arrudalabs.security;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class JwtTokenBuilder {

    private final String privateKeyId = UUID.randomUUID().toString();
    @ConfigProperty(name = "jwt.private.key")
    private String privateKey;

    public String generateToken(String username, Set<String> roles, Long durationInMinutes, String issuer) throws Exception {
        PrivateKey privateKey = decodePrivateKey();

        JwtClaimsBuilder claimsBuilder = Jwt.claims();
        long currentTimeInSecs = currentTimeInSecs();

        Set<String> groups =
                roles.stream().collect(Collectors.toSet());

        claimsBuilder.issuer(issuer);
        claimsBuilder.subject(username);
        claimsBuilder.issuedAt(currentTimeInSecs);
        claimsBuilder.expiresAt(currentTimeInSecs + (durationInMinutes));
        claimsBuilder.groups(groups);

        return claimsBuilder.jws().keyId(privateKeyId).sign(privateKey);
    }


    public PrivateKey decodePrivateKey() throws Exception {
        byte[] encodedBytes = toEncodedBytes(this.privateKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(keySpec);
    }

    public byte[] toEncodedBytes(final String pemEncoded) {
        final String normalizedPem = removeBeginEnd(pemEncoded);
        return Base64.getDecoder().decode(normalizedPem);
    }

    public String removeBeginEnd(String pem) {
        pem = pem.replaceAll("-----BEGIN (.*)-----", "");
        pem = pem.replaceAll("-----END (.*)----", "");
        pem = pem.replaceAll("\r\n", "");
        pem = pem.replaceAll("\n", "");
        return pem.trim();
    }

    public int currentTimeInSecs() {
        long currentTimeMS = System.currentTimeMillis();
        return (int) (currentTimeMS / 1000);
    }

}