package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProviderTest {

    @Test
    void testEnumValues() {
        // Act & Assert
        assertEquals(Provider.LOCAL, Provider.valueOf("LOCAL"));
        assertEquals(Provider.GITHUB, Provider.valueOf("GITHUB"));
        assertEquals(Provider.GOOGLE, Provider.valueOf("GOOGLE"));
    }

    @Test
    void testEnumMethods() {
        // Arrange
        Provider[] providers = Provider.values();

        // Assert
        assertEquals(3, providers.length);
        assertEquals(Provider.LOCAL, providers[0]);
        assertEquals(Provider.GITHUB, providers[1]);
        assertEquals(Provider.GOOGLE, providers[2]);
    }
}
