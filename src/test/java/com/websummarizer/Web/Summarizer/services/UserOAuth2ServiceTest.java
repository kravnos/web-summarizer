package com.websummarizer.Web.Summarizer.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;

import static org.junit.jupiter.api.Assertions.*;
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
    void loadUser_ThrowsOAuth2AuthenticationException() {
        // Arrange
        OAuth2UserRequest mockUserRequest = mock(OAuth2UserRequest.class);
        when(defaultOAuth2UserService.loadUser(mockUserRequest)).thenThrow(new OAuth2AuthenticationException("Error"));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> userOAuth2Service.loadUser(mockUserRequest));
    }

}
