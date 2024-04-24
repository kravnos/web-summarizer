package com.websummarizer.Web.Summarizer.utils;

import org.junit.jupiter.api.Test;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class RSAKeyPropertiesTest {

    @Test
    void testConstructor() {
        // Create RSA key properties
        RSAKeyProperties rsaKeyProperties = new RSAKeyProperties();

        // Check if public key is not null
        RSAPublicKey publicKey = rsaKeyProperties.getPublicKey();
        assertNotNull(publicKey);

        // Check if private key is not null
        RSAPrivateKey privateKey = rsaKeyProperties.getPrivateKey();
        assertNotNull(privateKey);
    }
}
