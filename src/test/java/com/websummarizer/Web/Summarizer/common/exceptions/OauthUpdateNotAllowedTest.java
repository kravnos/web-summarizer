package com.websummarizer.Web.Summarizer.common.exceptions;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OauthUpdateNotAllowedTest {

    @Test
    void testGetMessage() {
        // Create instance of OauthUpdateNotAllowed
        OauthUpdateNotAllowed exception = new OauthUpdateNotAllowed();

        // Verify the exception message
        assertEquals("Oauth user is not allowed to change the credentials", exception.getMessage());
    }
}
