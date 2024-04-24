package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        String login_email = "test@example.com";
        String login_password = "password";

        // Act
        UserDTO userDTO = new UserDTO(login_email, login_password);

        // Assert
        assertNotNull(userDTO);
        assertEquals(login_email, userDTO.getLogin_email());
        assertEquals(login_password, userDTO.getLogin_password());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        UserDTO userDTO = new UserDTO();

        // Assert
        assertNotNull(userDTO);
        assertEquals(null, userDTO.getLogin_email());
        assertEquals(null, userDTO.getLogin_password());
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        String login_email = "test@example.com";
        String login_password = "password";

        // Act
        userDTO.setLogin_email(login_email);
        userDTO.setLogin_password(login_password);

        // Assert
        assertEquals(login_email, userDTO.getLogin_email());
        assertEquals(login_password, userDTO.getLogin_password());
    }
}
