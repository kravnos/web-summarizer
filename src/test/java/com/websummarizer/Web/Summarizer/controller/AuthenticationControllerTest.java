package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.common.exceptions.OauthUpdateNotAllowed;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.UserReqAto;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {AuthenticationController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class AuthenticationControllerTest {
    @Autowired
    private AuthenticationController authenticationController;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private UserController userController;


    /**
     * Method under test: {@link AuthenticationController#updateUser(UserReqAto)}
     */
    @Test
    void testUpdateUser() {
        // Arrange and Act
        ResponseEntity<?> actualUpdateUserResult = (new AuthenticationController()).updateUser(null);

        // Assert
        assertEquals("Failed to update user.", actualUpdateUserResult.getBody());
        assertEquals(500, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }

    /**
     * Method under test: {@link AuthenticationController#updateUser(UserReqAto)}
     */
    @Test
    void testUpdateUser2() {
        // Arrange and Act
        ResponseEntity<?> actualUpdateUserResult = (new AuthenticationController()).updateUser(mock(UserReqAto.class));

        // Assert
        assertEquals("Failed to update user.", actualUpdateUserResult.getBody());
        assertEquals(500, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }

    @Test
    void testLoginUser_Successful() {
        // Mock request data
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin_email("test@example.com");
        userDTO.setLogin_password("password");

        // Mock service response
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setUser(new User());
        when(authenticationService.loginUser(userDTO.getLogin_email(), userDTO.getLogin_password())).thenReturn(loginResponseDTO);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.loginUser(userDTO);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(loginResponseDTO, responseEntity.getBody());
        verify(authenticationService, times(1)).loginUser(userDTO.getLogin_email(), userDTO.getLogin_password());
    }

    @Test
    void testLoginUser_InvalidCredentials() {
        // Mock request data
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin_email("test@example.com");
        userDTO.setLogin_password("password");

        // Mock service response with null user
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        when(authenticationService.loginUser(userDTO.getLogin_email(), userDTO.getLogin_password())).thenReturn(loginResponseDTO);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.loginUser(userDTO);

        // Verify the response
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertEquals("Invalid email or password.", responseEntity.getBody());
        verify(authenticationService, times(1)).loginUser(userDTO.getLogin_email(), userDTO.getLogin_password());
    }

    @Test
    void testLoginUser_Exception() {
        // Mock request data
        UserDTO userDTO = new UserDTO();
        userDTO.setLogin_email("test@example.com");
        userDTO.setLogin_password("password");

        // Mock service throwing an exception
        when(authenticationService.loginUser(userDTO.getLogin_email(), userDTO.getLogin_password())).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.loginUser(userDTO);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        verify(authenticationService, times(1)).loginUser(userDTO.getLogin_email(), userDTO.getLogin_password());
    }


    @Test
    void testUpdateUser_Successful() {
        // Mock request data
        UserReqAto userReqAto = UserReqAto.builder()
                .first_name("John")
                .last_name("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phone_number("1234567890")
                .build();

        // Mock service response
        User updatedUser = new User();
        updatedUser.setId(1L);
        when(authenticationService.updateExistingUser(userReqAto)).thenReturn(updatedUser);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.updateUser(userReqAto);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(updatedUser, responseEntity.getBody());
        verify(authenticationService, times(1)).updateExistingUser(userReqAto);
    }

    @Test
    void testUpdateUser_NullUser() {
        // Mock request data
        UserReqAto userReqAto = UserReqAto.builder()
                .first_name("John")
                .last_name("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phone_number("1234567890")
                .build();

        // Mock service returning null user
        when(authenticationService.updateExistingUser(userReqAto)).thenReturn(null);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.updateUser(userReqAto);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to update user.", responseEntity.getBody());
        verify(authenticationService, times(1)).updateExistingUser(userReqAto);
    }

    @Test
    void testUpdateUser_OauthUpdateNotAllowed() {
        // Mock request data
        UserReqAto userReqAto = UserReqAto.builder()
                .first_name("John")
                .last_name("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phone_number("1234567890")
                .build();

        // Mock service throwing OauthUpdateNotAllowed exception
        when(authenticationService.updateExistingUser(userReqAto)).thenThrow(new OauthUpdateNotAllowed());

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.updateUser(userReqAto);

        // Verify the response
        assertEquals(HttpStatus.FORBIDDEN, responseEntity.getStatusCode());
        assertEquals("Cannot update information for google/github users", responseEntity.getBody());
        verify(authenticationService, times(1)).updateExistingUser(userReqAto);
    }

    @Test
    void testUpdateUser_Exception() {
        // Mock request data
        UserReqAto userReqAto = UserReqAto.builder()
                .first_name("John")
                .last_name("Doe")
                .email("john.doe@example.com")
                .password("password")
                .phone_number("1234567890")
                .build();

        // Mock service throwing an exception
        when(authenticationService.updateExistingUser(userReqAto)).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.updateUser(userReqAto);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to update user.", responseEntity.getBody());
        verify(authenticationService, times(1)).updateExistingUser(userReqAto);
    }

    @Test
    void testRegisterUser_Successful() {
        // Mock request data
        User user = new User();
        user.setEmail("john.doe@example.com");

        // Mock service response
        User registeredUser = new User();
        registeredUser.setEmail("john.doe@example.com");
        when(authenticationService.registerUser(user)).thenReturn(registeredUser);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.registerUser(user);

        // Verify the response
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(registeredUser, responseEntity.getBody());
        verify(authenticationService, times(1)).registerUser(user);
    }

    @Test
    void testRegisterUser_NullUser() {
        // Mock request data
        User user = new User();
        user.setEmail("john.doe@example.com");

        // Mock service returning null user
        when(authenticationService.registerUser(user)).thenReturn(null);

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.registerUser(user);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to register user.", responseEntity.getBody());
        verify(authenticationService, times(1)).registerUser(user);
    }

    @Test
    void testRegisterUser_Exception() {
        // Mock request data
        User user = new User();
        user.setEmail("john.doe@example.com");

        // Mock service throwing an exception
        when(authenticationService.registerUser(user)).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<?> responseEntity = authenticationController.registerUser(user);

        // Verify the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Failed to register user.", responseEntity.getBody());
        verify(authenticationService, times(1)).registerUser(user);
    }
}
