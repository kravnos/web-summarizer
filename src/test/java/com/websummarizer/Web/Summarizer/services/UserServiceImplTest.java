package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepo userRepo;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void testCreateUser() {
        // Arrange
        User user = new User();
        when(userRepo.save(user)).thenReturn(user);

        // Act
        User result = userService.createUser(user);

        // Assert
        assertEquals(user, result);
        verify(userRepo, times(1)).save(user);
    }

    @Test
    void testGetUserByEmailAndResetToken() {
        // Arrange
        String email = "test@example.com";
        String token = "token";
        User expectedUser = new User();
        when(userRepo.getUserByEmailAndResetToken(email, token)).thenReturn(expectedUser);

        // Act
        User result = userService.getUserByEmailAndResetToken(email, token);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testSetPasswordRequestToken() {
        // Arrange
        String token = "token";
        User user = new User();

        // Act
        userService.setPasswordRequestToken(token, user);

        // Assert
        verify(userRepo, times(1)).setRequestToken(token, user.getEmail());
    }

    @Test
    void testLoadUserByUsername_UserFound() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        assertDoesNotThrow(() -> userService.loadUserByUsername(email));
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(email));
    }

    @Test
    void testGetUserName_UserFound() {
        // Arrange
        long id = 1L;
        User user = new User();
        user.setFirst_name("John");
        when(userRepo.findById(id)).thenReturn(Optional.of(user));

        // Act
        String result = userService.getUserName(id);

        // Assert
        assertEquals("John", result);
    }

    @Test
    void testGetUserName_UserNotFound() {
        // Arrange
        long id = 1L;
        when(userRepo.findById(id)).thenReturn(Optional.empty());

        // Act
        String result = userService.getUserName(id);

        // Assert
        assertEquals("You", result);
    }

    @Test
    void testGetFoundId_UserFound() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setId(1L);
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Long result = userService.getFoundId(email);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    void testGetFoundId_UserNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act
        Long result = userService.getFoundId(email);

        // Assert
        assertEquals(1L, result);
    }

    @Test
    void testGetFoundUser_UserFound() {
        // Arrange
        String email = "test@example.com";
        User expectedUser = new User();
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(expectedUser));

        // Act
        User result = userService.getFoundUser(email);

        // Assert
        assertEquals(expectedUser, result);
    }

    @Test
    void testGetFoundUser_UserNotFound() {
        // Arrange
        String email = "test@example.com";
        when(userRepo.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> userService.getFoundUser(email));
    }
}
