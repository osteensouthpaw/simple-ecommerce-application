package com.omega.simpleecommerceapplication.user.jwtUtils;

import java.security.*;

public class KeyGeneratorUtility {

    public static KeyPair getKeyPairGenerator()
            throws NoSuchAlgorithmException {
        KeyPairGenerator rsaKey = KeyPairGenerator.getInstance("RSA");
        rsaKey.initialize(2048);
        return rsaKey.generateKeyPair();
    }
}

