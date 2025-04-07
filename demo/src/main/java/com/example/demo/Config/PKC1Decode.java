package com.example.demo.Config;

import java.io.FileReader;
import java.nio.file.Paths;
import java.security.Security;
import java.util.Base64;

import com.example.demo.Services.GitService;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.security.PrivateKey;

public class PKC1Decode
{

    public static String decode() throws Exception
    {
        Security.addProvider(new BouncyCastleProvider());

        if(!Paths.get(GitService.getPath()).toFile().exists())
        {
            System.out.println("\nThe path is incorrect." +
                    "\nCheck your private key name and directory and try again.\n" );

        }


        PEMParser pemParser = new PEMParser(new FileReader(GitService.getPath()));
        Object object = pemParser.readObject();

        //checking and decoding if given file is a PKCS#1 key
        if (!(object instanceof PEMKeyPair))
        {
            System.out.println("\nGiven file is not a PKCS#1 file." +
                    "\nCheck your PKCS#1 file and try again.\n"  );
        }


            JcaPEMKeyConverter converter = new JcaPEMKeyConverter().setProvider("BC");
            PrivateKey privateKey = converter.getKeyPair((PEMKeyPair) object).getPrivate();


            final byte[] encoded = privateKey.getEncoded();
            final PrivateKeyInfo privateKeyInfo = PrivateKeyInfo.getInstance(encoded);
            final ASN1Encodable encodable = privateKeyInfo.parsePrivateKey();
            final ASN1Primitive primitive = encodable.toASN1Primitive();
            byte[] toPKCS8 = primitive.getEncoded(ASN1Encoding.DER);
            return Base64.getEncoder().encodeToString(toPKCS8);



    }
}