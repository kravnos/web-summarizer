package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserRepoTest {

    @Mock
    private UserRepo userRepo;

    @Test
    void testGetUserByEmailAndResetToken() {
        // Arrange
        String email = "test@example.com";
        String token = "token";
        User user = new User();
        when(userRepo.getUserByEmailAndResetToken(email, token)).thenReturn(user);

        // Act
        User result = userRepo.getUserByEmailAndResetToken(email, token);

        // Assert
        assertEquals(user, result);
    }

    @Test
    void testSetRequestToken() {
        // Arrange
        String email = "test@example.com";
        String token = "token";

        // Act
        userRepo.setRequestToken(token, email);

        // Assert
        verify(userRepo, times(1)).setRequestToken(token, email);
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        when(userRepo.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userRepo.findByEmail(email);

        // Assert
        assertEquals(user, result.orElse(null));
    }
}
