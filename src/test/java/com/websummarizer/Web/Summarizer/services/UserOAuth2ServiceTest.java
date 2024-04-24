package com.websummarizer.Web.Summarizer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserOAuth2ServiceTest {

    private DefaultOAuth2UserService defaultOAuth2UserService;
    private UserOAuth2Service userOAuth2Service;

    @BeforeEach
    void setUp() {
        defaultOAuth2UserService = mock(DefaultOAuth2UserService.class);
        userOAuth2Service = new UserOAuth2Service();
    }

    @Test
    void loadUser() throws OAuth2AuthenticationException {
        // Create a mock OAuth2UserRequest
        OAuth2UserRequest mockUserRequest = mock(OAuth2UserRequest.class);
        OAuth2User mockOAuth2User = mock(OAuth2User.class);
        when(defaultOAuth2UserService.loadUser(mockUserRequest)).thenReturn(mockOAuth2User);

        // Call the loadUser method of UserOAuth2Service
        OAuth2User userOAuth2 = userOAuth2Service.loadUser(mockUserRequest);

        assertNotNull(userOAuth2);
        // Add more assertions if needed
    }
}
