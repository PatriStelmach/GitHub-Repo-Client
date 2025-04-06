package com.example.demo.Config;

import java.io.FileReader;
import java.nio.file.Paths;
import java.security.KeyException;
import java.security.Security;
import java.util.Base64;

import com.example.demo.Errors.ClientErrorDecoder;
import com.example.demo.Errors.PKCS1KeyException;
import com.example.demo.Errors.UserNotFoundException;
import feign.codec.ErrorDecoder;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.context.annotation.Bean;

import java.security.PrivateKey;

public class PKC1Decode
{

    public static String decode() throws Exception
    {
        String address = "demo/src/main/resources/github-key.pem";
        Security.addProvider(new BouncyCastleProvider());

        if(!Paths.get(address).toFile().exists())
        {
            throw new PKCS1KeyException("");
        }

        PEMParser pemParser = new PEMParser(new FileReader(address));
        Object object = pemParser.readObject();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
        PrivateKey privateKey;

        privateKey = converter.getKeyPair((PEMKeyPair) object).getPrivate();

            final byte[] encoded = privateKey.getEncoded();
            final PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(encoded);
            final ASN1Encodable encodable = privateKeyInfo.parsePrivateKey();
            final ASN1Primitive primitive = encodable.toASN1Primitive();
            byte[] toPKCS8 = primitive.getEncoded(ASN1Encoding.DER);
            return Base64.getEncoder().encodeToString(toPKCS8);

    }
}