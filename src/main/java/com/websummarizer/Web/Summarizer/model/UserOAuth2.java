package com.websummarizer.Web.Summarizer.model;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

/**
 * Wrapper class for OAuth2User providing convenient methods to access user attributes.
 */
@AllArgsConstructor
public class UserOAuth2 implements OAuth2User {

    private OAuth2User oAuth2User;

    /**
     * Get the user's attributes.
     *
     * @return A map containing user attributes.
     */
    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2User.getAttributes();
    }

    /**
     * Get the user's authorities.
     *
     * @return A collection of granted authorities.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return oAuth2User.getAuthorities();
    }

    /**
     * Get the user's name.
     *
     * @return The user's name.
     */
    @Override
    public String getName() {
        return oAuth2User.getName();
    }

    /**
     * Get the user's email address.
     *
     * @return The user's email address.
     */
    public String getEmail() {
        return oAuth2User.getAttribute("email");
    }

    /**
     * Get the user's login.
     *
     * @return The user's login.
     */
    public String getLogin() {
        return oAuth2User.getAttribute("login");
    }
}
