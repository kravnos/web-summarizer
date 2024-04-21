package com.websummarizer.Web.Summarizer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceImplDiffblueTest {
    @MockBean
    private UserRepo userRepo;

    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link UserServiceImpl#createUser(User)}
     */
    @Test
    void testCreateUser() {
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
        User actualCreateUserResult = userServiceImpl.createUser(user2);

        // Assert
        verify(userRepo).save(isA(User.class));
        assertSame(user, actualCreateUserResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#createUser(User)}
     */
    @Test
    void testCreateUser2() {
        // Arrange
        when(userRepo.save(Mockito.<User>any())).thenThrow(new UsernameNotFoundException("Msg"));

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
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.createUser(user));
        verify(userRepo).save(isA(User.class));
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#getUserByEmailAndResetToken(String, String)}
     */
    @Test
    void testGetUserByEmailAndResetToken() {
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
        when(userRepo.getUserByEmailAndResetToken(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);

        // Act
        User actualUserByEmailAndResetToken = userServiceImpl.getUserByEmailAndResetToken("jane.doe@example.org", "ABC123");

        // Assert
        verify(userRepo).getUserByEmailAndResetToken(eq("jane.doe@example.org"), eq("ABC123"));
        assertSame(user, actualUserByEmailAndResetToken);
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#getUserByEmailAndResetToken(String, String)}
     */
    @Test
    void testGetUserByEmailAndResetToken2() {
        // Arrange
        when(userRepo.getUserByEmailAndResetToken(Mockito.<String>any(), Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("Msg"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class,
                () -> userServiceImpl.getUserByEmailAndResetToken("jane.doe@example.org", "ABC123"));
        verify(userRepo).getUserByEmailAndResetToken(eq("jane.doe@example.org"), eq("ABC123"));
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#setPasswordRequestToken(String, User)}
     */
    @Test
    void testSetPasswordRequestToken() {
        // Arrange
        doNothing().when(userRepo).setRequestToken(Mockito.<String>any(), Mockito.<String>any());

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
        userServiceImpl.setPasswordRequestToken("ABC123", user);

        // Assert that nothing has changed
        verify(userRepo).setRequestToken(eq("ABC123"), eq("jane.doe@example.org"));
        assertEquals("6625550144", user.getPhone_number());
        assertEquals("ABC123", user.getRequest_token());
        assertEquals("Doe", user.getLast_name());
        assertEquals("Jane", user.getFirst_name());
        assertEquals("Llm Selection", user.getLlmSelection());
        assertEquals("iloveyou", user.getPassword());
        assertEquals("jane.doe@example.org", user.getEmail());
        assertEquals(1L, user.getId());
        assertEquals(Provider.LOCAL, user.getProvider());
        assertTrue(user.getAuthorities().isEmpty());
    }

    /**
     * Method under test:
     * {@link UserServiceImpl#setPasswordRequestToken(String, User)}
     */
    @Test
    void testSetPasswordRequestToken2() {
        // Arrange
        doThrow(new UsernameNotFoundException("Msg")).when(userRepo)
                .setRequestToken(Mockito.<String>any(), Mockito.<String>any());

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
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.setPasswordRequestToken("ABC123", user));
        verify(userRepo).setRequestToken(eq("ABC123"), eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername() throws UsernameNotFoundException {
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

        // Act
        UserDetails actualLoadUserByUsernameResult = userServiceImpl.loadUserByUsername("jane.doe@example.org");

        // Assert
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
        assertSame(user, actualLoadUserByUsernameResult);
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername2() throws UsernameNotFoundException {
        // Arrange
        Optional<User> emptyResult = Optional.empty();
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(emptyResult);

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("jane.doe@example.org"));
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
    }

    /**
     * Method under test: {@link UserServiceImpl#loadUserByUsername(String)}
     */
    @Test
    void testLoadUserByUsername3() throws UsernameNotFoundException {
        // Arrange
        when(userRepo.findByEmail(Mockito.<String>any())).thenThrow(new UsernameNotFoundException("Msg"));

        // Act and Assert
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("jane.doe@example.org"));
        verify(userRepo).findByEmail(eq("jane.doe@example.org"));
    }
}
