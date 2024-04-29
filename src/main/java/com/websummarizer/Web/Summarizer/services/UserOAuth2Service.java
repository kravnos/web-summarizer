package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.UserOAuth2;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

/**
 * Custom OAuth2 user service to load user details from OAuth2 providers.
 */
@Service
public class UserOAuth2Service extends DefaultOAuth2UserService {

    /**
     * Load user details from the OAuth2 provider.
     *
     * @param userRequest The OAuth2 user request.
     * @return An OAuth2User object containing the user details.
     * @throws OAuth2AuthenticationException If an error occurs during authentication.
     */
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // Call the superclass method to load user details from the OAuth2 provider
        OAuth2User userOAuth2 = super.loadUser(userRequest);
        // Wrap the retrieved OAuth2User object into a custom UserOAuth2 object
        return new UserOAuth2(userOAuth2);
    }

}
