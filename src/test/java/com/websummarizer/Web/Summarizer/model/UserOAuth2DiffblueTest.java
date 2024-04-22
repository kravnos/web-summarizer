package com.websummarizer.Web.Summarizer.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

class UserOAuth2DiffblueTest {
    /**
     * Method under test: {@link UserOAuth2#getAttributes()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAttributes() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: expiresAt must be after issuedAt
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Instant issuedAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
        Instant expiresAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

        // Act
        (new UserOAuth2(new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>()))))
                .getAttributes();
    }

    /**
     * Method under test: {@link UserOAuth2#getAuthorities()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAuthorities() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: expiresAt must be after issuedAt
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Instant issuedAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
        Instant expiresAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

        // Act
        (new UserOAuth2(new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>()))))
                .getAuthorities();
    }

    /**
     * Method under test: {@link UserOAuth2#getName()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetName() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: expiresAt must be after issuedAt
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Instant issuedAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
        Instant expiresAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

        // Act
        (new UserOAuth2(new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>()))))
                .getName();
    }

    /**
     * Method under test: {@link UserOAuth2#getEmail()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetEmail() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: expiresAt must be after issuedAt
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Instant issuedAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
        Instant expiresAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

        // Act
        (new UserOAuth2(new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>()))))
                .getEmail();
    }

    /**
     * Method under test: {@link UserOAuth2#getLogin()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetLogin() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.IllegalArgumentException: expiresAt must be after issuedAt
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        Instant issuedAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();
        Instant expiresAt = LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant();

        // Act
        (new UserOAuth2(new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>()))))
                .getLogin();
    }
}
