package com.websummarizer.Web.Summarizer.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class KeyGeneratorUtilityDiffblueTest {
    /**
     * Method under test: {@link KeyGeneratorUtility#generateRsaKey()}
     */
    @Test
    void testGenerateRsaKey() {
        // Arrange and Act
        Assertions.assertNotNull(KeyGeneratorUtility.generateRsaKey());
    }
}
