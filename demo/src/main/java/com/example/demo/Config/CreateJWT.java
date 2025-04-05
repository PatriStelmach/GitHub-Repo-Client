package com.example.demo.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

import static java.lang.String.valueOf;

public class CreateJWT
{

    private static final long APP_ID = 1201772;

    public static String generateJWT() throws Exception
    {
        String privateKeyContent = new String(Files.readAllBytes(Paths
                .get("demo/src/main/resources/git_pkcs8.pem")));


        privateKeyContent = privateKeyContent
                .replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s+", "");

        byte[] decoded = Base64.getDecoder().decode(privateKeyContent);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(decoded);
        RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(keySpec);

        Algorithm algorithm = Algorithm.RSA256(null, privateKey);

        return JWT.create()
                .withIssuer(valueOf(APP_ID))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(360))
                .sign(algorithm);
    }

}
