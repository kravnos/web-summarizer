package com.websummarizer.Web.Summarizer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserOAuth2;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OAuth2AuthenticationService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class OAuth2AuthenticationServiceDiffblueTest {
    @Autowired
    private OAuth2AuthenticationService oAuth2AuthenticationService;

    @MockBean
    private RoleRepo roleRepo;

    @MockBean
    private UserRepo userRepo;

    /**
     * Method under test: {@link OAuth2AuthenticationService#registerUser(User)}
     */
    @Test
    void testRegisterUser() {
        // Arrange
        Role role = new Role();
        role.setAuthority("JaneDoe");
        role.setRoleId(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(ofResult);

        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");
        when(userRepo.save(Mockito.<User>any())).thenReturn(user);

        User user2 = new User();
        user2.setAuthorities(new HashSet<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(1L);
        user2.setLast_name("Doe");
        user2.setLlmSelection("Llm Selection");
        user2.setPassword("iloveyou");
        user2.setPhone_number("6625550144");
        user2.setProvider(Provider.LOCAL);
        user2.setRequest_token("ABC123");

        // Act
        User actualRegisterUserResult = oAuth2AuthenticationService.registerUser(user2);

        // Assert
        verify(roleRepo).findByAuthority(eq("USER"));
        verify(userRepo).save(isA(User.class));
        assertEquals(1, user2.getAuthorities().size());
        assertSame(user, actualRegisterUserResult);
    }

    /**
     * Method under test: {@link OAuth2AuthenticationService#registerUser(User)}
     */
    @Test
    void testRegisterUser2() {
        // Arrange
        Role role = new Role();
        role.setAuthority("JaneDoe");
        role.setRoleId(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(ofResult);
        when(userRepo.save(Mockito.<User>any())).thenThrow(new IllegalStateException("USER"));

        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> oAuth2AuthenticationService.registerUser(user));
        verify(roleRepo).findByAuthority(eq("USER"));
        verify(userRepo).save(isA(User.class));
    }

    /**
     * Method under test: {@link OAuth2AuthenticationService#registerUser(User)}
     */
    @Test
    void testRegisterUser3() {
        // Arrange
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(emptyResult);

        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");

        // Act and Assert
        assertThrows(IllegalStateException.class, () -> oAuth2AuthenticationService.registerUser(user));
        verify(roleRepo).findByAuthority(eq("USER"));
    }

    /**
     * Method under test:
     * {@link OAuth2AuthenticationService#processOAuthPostLoginGoogle(DefaultOidcUser)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testProcessOAuthPostLoginGoogle() {
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
        oAuth2AuthenticationService.processOAuthPostLoginGoogle(
                new DefaultOidcUser(authorities, new OidcIdToken("ABC123", issuedAt, expiresAt, new HashMap<>())));
    }

    /**
     * Method under test:
     * {@link OAuth2AuthenticationService#processOAuthPostLoginGithub(UserOAuth2)}
     */
    @Test
    void testProcessOAuthPostLoginGithub() {
        // Arrange
        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        UserOAuth2 oAuth2User = mock(UserOAuth2.class);
        when(oAuth2User.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

        // Act
        User actualProcessOAuthPostLoginGithubResult = oAuth2AuthenticationService
                .processOAuthPostLoginGithub(new UserOAuth2(oAuth2User));

        // Assert
        verify(userRepo).findByEmail(eq("Attribute"));
        verify(oAuth2User).getAttribute(eq("login"));
        assertNull(actualProcessOAuthPostLoginGithubResult);
    }

    /**
     * Method under test:
     * {@link OAuth2AuthenticationService#processOAuthPostLoginGithub(UserOAuth2)}
     */
    @Test
    void testProcessOAuthPostLoginGithub2() {
        // Arrange
        Role role = new Role();
        role.setAuthority("JaneDoe");
        role.setRoleId(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(ofResult);

        User user = new User();
        user.setAuthorities(new HashSet<>());
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setLlmSelection("Llm Selection");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        user.setProvider(Provider.LOCAL);
        user.setRequest_token("ABC123");
        when(userRepo.save(Mockito.<User>any())).thenReturn(user);
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        UserOAuth2 oAuth2User = mock(UserOAuth2.class);
        when(oAuth2User.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

        // Act
        User actualProcessOAuthPostLoginGithubResult = oAuth2AuthenticationService
                .processOAuthPostLoginGithub(new UserOAuth2(oAuth2User));

        // Assert
        verify(roleRepo).findByAuthority(eq("USER"));
        verify(userRepo).findByEmail(eq("Attribute"));
        verify(userRepo).save(isA(User.class));
        verify(oAuth2User).getAttribute(eq("login"));
        assertSame(user, actualProcessOAuthPostLoginGithubResult);
    }

    /**
     * Method under test:
     * {@link OAuth2AuthenticationService#processOAuthPostLoginGithub(UserOAuth2)}
     */
    @Test
    void testProcessOAuthPostLoginGithub3() {
        // Arrange
        Role role = new Role();
        role.setAuthority("JaneDoe");
        role.setRoleId(1L);
        Optional<Role> ofResult = Optional.of(role);
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(ofResult);
        when(userRepo.save(Mockito.<User>any())).thenThrow(new IllegalStateException("login"));
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);
        UserOAuth2 oAuth2User = mock(UserOAuth2.class);
        when(oAuth2User.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

        // Act and Assert
        assertThrows(IllegalStateException.class,
                () -> oAuth2AuthenticationService.processOAuthPostLoginGithub(new UserOAuth2(oAuth2User)));
        verify(roleRepo).findByAuthority(eq("USER"));
        verify(userRepo).findByEmail(eq("Attribute"));
        verify(userRepo).save(isA(User.class));
        verify(oAuth2User).getAttribute(eq("login"));
    }

    /**
     * Method under test:
     * {@link OAuth2AuthenticationService#processOAuthPostLoginGithub(UserOAuth2)}
     */
    @Test
    void testProcessOAuthPostLoginGithub4() {
        // Arrange
        Optional<Role> emptyResult = Optional.empty();
        when(roleRepo.findByAuthority(Mockito.<String>any())).thenReturn(emptyResult);
        Optional<User> emptyResult2 = Optional.empty();
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(emptyResult2);
        UserOAuth2 oAuth2User = mock(UserOAuth2.class);
        when(oAuth2User.getAttribute(Mockito.<String>any())).thenReturn("Attribute");

        // Act and Assert
        assertThrows(IllegalStateException.class,
                () -> oAuth2AuthenticationService.processOAuthPostLoginGithub(new UserOAuth2(oAuth2User)));
        verify(roleRepo).findByAuthority(eq("USER"));
        verify(userRepo).findByEmail(eq("Attribute"));
        verify(oAuth2User).getAttribute(eq("login"));
    }
}
