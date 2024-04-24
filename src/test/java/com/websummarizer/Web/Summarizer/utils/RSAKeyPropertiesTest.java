package com.websummarizer.Web.Summarizer.utils;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RSAKeyPropertiesTest {

    @Test
    void testConstructor() throws Exception {
        // Arrange
        KeyPair keyPair = mock(KeyPair.class);
        RSAPublicKey publicKey = mock(RSAPublicKey.class);
        RSAPrivateKey privateKey = mock(RSAPrivateKey.class);
        when(keyPair.getPublic()).thenReturn(publicKey);
        when(keyPair.getPrivate()).thenReturn(privateKey);
        KeyPairGenerator keyPairGenerator = mock(KeyPairGenerator.class);
        when(keyPairGenerator.generateKeyPair()).thenReturn(keyPair);
        RSAKeyProperties rsaKeyProperties = new RSAKeyProperties();

        // Act
        RSAPublicKey resultPublicKey = rsaKeyProperties.getPublicKey();
        RSAPrivateKey resultPrivateKey = rsaKeyProperties.getPrivateKey();

        // Assert
        assertEquals(publicKey, resultPublicKey);
        assertEquals(privateKey, resultPrivateKey);
    }
}
