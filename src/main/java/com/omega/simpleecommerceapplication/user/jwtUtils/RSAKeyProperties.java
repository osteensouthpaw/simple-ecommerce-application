package com.omega.simpleecommerceapplication.user.jwtUtils;


import lombok.Data;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Data
@Component
public class RSAKeyProperties {
    private RSAPublicKey publicKey;
    private  RSAPrivateKey privateKey;

    public RSAKeyProperties() throws NoSuchAlgorithmException {
        KeyPair keyPairGenerator = KeyGeneratorUtility.getKeyPairGenerator();
        privateKey = (RSAPrivateKey) keyPairGenerator.getPrivate();
        publicKey = (RSAPublicKey) keyPairGenerator.getPublic();
    }
}
