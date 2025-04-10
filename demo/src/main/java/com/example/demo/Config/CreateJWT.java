package com.example.demo.Config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.Services.GitService;
import lombok.RequiredArgsConstructor;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

import static java.lang.String.valueOf;

@RequiredArgsConstructor
public class CreateJWT
{

    public static String generateJWTFromPKCS1File() throws Exception
    {


        String privateKeyContent = PKC1Decode.decode();

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
                .withIssuer(valueOf(GitService.getAppId()))
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusSeconds(360))
                .sign(algorithm);


    }
}