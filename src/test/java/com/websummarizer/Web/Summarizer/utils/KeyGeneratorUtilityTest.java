package com.websummarizer.Web.Summarizer.utils;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class KeyGeneratorUtilityTest {

    @Test
    void testGenerateRsaKey() throws NoSuchAlgorithmException {
        // Arrange
        KeyPairGenerator keyPairGenerator = mock(KeyPairGenerator.class);
        KeyPair keyPair = mock(KeyPair.class);

        when(keyPairGenerator.generateKeyPair()).thenReturn(keyPair);
        when(KeyPairGenerator.getInstance("RSA")).thenReturn(keyPairGenerator);

        // Act
        KeyPair result = KeyGeneratorUtility.generateRsaKey();

        // Assert
        assertNotNull(result);
        assertEquals(keyPair, result);
    }
}
