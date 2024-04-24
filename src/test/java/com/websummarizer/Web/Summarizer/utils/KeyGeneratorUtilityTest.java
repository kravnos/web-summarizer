package com.websummarizer.Web.Summarizer.utils;

import org.junit.jupiter.api.Test;

import java.security.KeyPair;

import static org.junit.jupiter.api.Assertions.*;

class KeyGeneratorUtilityTest {

    @Test
    void testGenerateRsaKey() {
        // Test key generation
        KeyPair keyPair = KeyGeneratorUtility.generateRsaKey();

        // Verify key pair is not null
        assertNotNull(keyPair);

        // Verify public and private keys are not null
        assertNotNull(keyPair.getPublic());
        assertNotNull(keyPair.getPrivate());
    }
}
