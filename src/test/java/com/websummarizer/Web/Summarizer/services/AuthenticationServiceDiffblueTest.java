package com.websummarizer.Web.Summarizer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AuthenticationService.class, AuthenticationManager.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticationServiceDiffblueTest {
    @MockBean
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private RoleRepo roleRepo;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserRepo userRepo;

    /**
     * Method under test: {@link AuthenticationService#registerUser(User)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testRegisterUser() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@4e3a3e8f testClass = com.websummarizer.Web.Summarizer.services.DiffblueFakeClass261, locations = [], classes = [com.websummarizer.Web.Summarizer.services.AuthenticationService, org.springframework.security.authentication.AuthenticationManager, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@a0c7f62, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@6fa48cc4, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@d8eb0cc8, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@422376f9], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

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

        // Act
        authenticationService.registerUser(user);
    }

    /**
     * Method under test: {@link AuthenticationService#loginUser(String, String)}
     */
    @Test
    void testLoginUser() throws AuthenticationException {
        // Arrange
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
        when(tokenService.generateJwt(Mockito.<Authentication>any())).thenReturn("Generate Jwt");

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

        // Act
        LoginResponseDTO actualLoginUserResult = authenticationService.loginUser("jane.doe@example.org", "iloveyou");

        // Assert
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
        verify(tokenService).generateJwt(isA(Authentication.class));
        verify(authenticationManager).authenticate(isA(Authentication.class));
        assertEquals("Generate Jwt", actualLoginUserResult.getJwt());
        assertTrue(actualLoginUserResult.getUser().isAccountNonExpired());
    }

    /**
     * Method under test: {@link AuthenticationService#loginUser(String, String)}
     */
    @Test
    void testLoginUser2() throws AuthenticationException {
        // Arrange
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
        when(tokenService.generateJwt(Mockito.<Authentication>any())).thenReturn("Generate Jwt");
        when(userRepo.findByEmail(Mockito.<String>any()))
                .thenThrow(new AccountExpiredException("User login successful: {0}"));

        // Act
        LoginResponseDTO actualLoginUserResult = authenticationService.loginUser("jane.doe@example.org", "iloveyou");

        // Assert
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
        verify(tokenService).generateJwt(isA(Authentication.class));
        verify(authenticationManager).authenticate(isA(Authentication.class));
        assertEquals("", actualLoginUserResult.getJwt());
        assertNull(actualLoginUserResult.getUser());
    }

    /**
     * Method under test: {@link AuthenticationService#loginUser(String, String)}
     */
    @Test
    void testLoginUser3() throws AuthenticationException {
        // Arrange
        when(authenticationManager.authenticate(Mockito.<Authentication>any()))
                .thenReturn(new BearerTokenAuthenticationToken("ABC123"));
        when(tokenService.generateJwt(Mockito.<Authentication>any())).thenReturn("Generate Jwt");
        when(userRepo.findByEmail(Mockito.<String>any())).thenThrow(new RuntimeException("User login successful: {0}"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> authenticationService.loginUser("jane.doe@example.org", "iloveyou"));
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
        verify(tokenService).generateJwt(isA(Authentication.class));
        verify(authenticationManager).authenticate(isA(Authentication.class));
    }

    /**
     * Method under test:
     * {@link AuthenticationService#updateExistingUser(UserReqAto)}
     */
    @Test
    void testUpdateExistingUser() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange, Act and Assert
        assertNull((new AuthenticationService()).updateExistingUser(null));
    }

    /**
     * Method under test:
     * {@link AuthenticationService#updateExistingUser(UserReqAto)}
     */
    @Test
    void testUpdateExistingUser2() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AuthenticationService authenticationService = new AuthenticationService();
        UserReqAto userReqAto = UserReqAto.builder()
                .account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .request_token("ABC123")
                .build();

        // Act and Assert
        assertNull(authenticationService.updateExistingUser(userReqAto));
    }

    /**
     * Method under test:
     * {@link AuthenticationService#updateExistingUser(UserReqAto)}
     */
    @Test
    void testUpdateExistingUser3() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        AuthenticationService authenticationService = new AuthenticationService();
        UserReqAto.UserReqAtoBuilder userReqAtoBuilder = mock(UserReqAto.UserReqAtoBuilder.class);
        when(userReqAtoBuilder.account_llm(Mockito.<String>any())).thenReturn(UserReqAto.builder());
        UserReqAto userReqAto = userReqAtoBuilder.account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .request_token("ABC123")
                .build();

        // Act
        User actualUpdateExistingUserResult = authenticationService.updateExistingUser(userReqAto);

        // Assert
        verify(userReqAtoBuilder).account_llm(eq("3"));
        assertNull(actualUpdateExistingUserResult);
    }

    /**
     * Method under test:
     * {@link AuthenticationService#updateExistingUser(UserReqAto)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testUpdateExistingUser4() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@136724aa testClass = com.websummarizer.Web.Summarizer.services.DiffblueFakeClass566, locations = [], classes = [com.websummarizer.Web.Summarizer.services.AuthenticationService, org.springframework.security.authentication.AuthenticationManager, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@a0c7f62, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@6fa48cc4, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@d8eb0cc8, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@422376f9], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.Optional.map(Optional.java:260)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        authenticationService.updateExistingUser(null);
    }
}
