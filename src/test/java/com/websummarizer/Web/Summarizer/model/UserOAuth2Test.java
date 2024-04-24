package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserOAuth2Test {

    private OAuth2User mockOAuth2User;
    private UserOAuth2 userOAuth2;

    @BeforeEach
    void setUp() {
        // Create a mock OAuth2User
        mockOAuth2User = mock(OAuth2User.class);
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", "test@example.com");
        attributes.put("login", "testuser");
        when(mockOAuth2User.getAttributes()).thenReturn(attributes);
        when(mockOAuth2User.getName()).thenReturn("Test User");

        // Create an instance of UserOAuth2
        userOAuth2 = new UserOAuth2(mockOAuth2User);
    }

    @Test
    void getEmail() {
        assertEquals(null, userOAuth2.getEmail());
    }

    @Test
    void getLogin() {
        assertEquals(null, userOAuth2.getLogin());
    }

    @Test
    void getAttributes() {
        assertEquals(mockOAuth2User.getAttributes(), userOAuth2.getAttributes());
    }

    @Test
    void getAuthorities() {
        Collection<? extends GrantedAuthority> authorities = userOAuth2.getAuthorities();
        // Add assertions for authorities if needed
    }

    @Test
    void getName() {
        assertEquals("Test User", userOAuth2.getName());
    }
}
