package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class LoginResponseDTOTest {

    @Test
    void testAllArgsConstructor() {
        // Arrange
        User user = new User();
        String jwt = "testToken";

        // Act
        LoginResponseDTO responseDTO = new LoginResponseDTO(user, jwt);

        // Assert
        assertNotNull(responseDTO);
        assertEquals(user, responseDTO.getUser());
        assertEquals(jwt, responseDTO.getJwt());
    }

    @Test
    void testNoArgsConstructor() {
        // Act
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        // Assert
        assertNotNull(responseDTO);
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        User user = new User();
        String jwt = "testToken";
        LoginResponseDTO responseDTO = new LoginResponseDTO();

        // Act
        responseDTO.setUser(user);
        responseDTO.setJwt(jwt);

        // Assert
        assertEquals(user, responseDTO.getUser());
        assertEquals(jwt, responseDTO.getJwt());
    }
}
