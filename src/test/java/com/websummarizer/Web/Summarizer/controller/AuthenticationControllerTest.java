package com.websummarizer.Web.Summarizer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.UserReqAto;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

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
     * Method under test: {@link AuthenticationController#registerUser(User)}
     */
    @Test
    void testRegisterUser() throws Exception {
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
        when(authenticationService.registerUser(Mockito.<User>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/register");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password\":\"iloveyou\","
                                        + "\"phone_number\":\"6625550144\",\"request_token\":\"ABC123\",\"authorities\":[],\"provider\":\"LOCAL\",\"llmSelection\":\"Llm"
                                        + " Selection\",\"enabled\":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"username\":\"jane.doe"
                                        + "@example.org\",\"accountNonLocked\":true}"));
    }

    /**
     * Method under test: {@link AuthenticationController#loginUser(UserDTO)}
     */
    @Test
    void testLoginUser() throws Exception {
        // Arrange
        when(authenticationService.loginUser(Mockito.<String>any(), Mockito.<String>any()))
                .thenReturn(new LoginResponseDTO(new User(), "Invalid email or password."));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/auth/login");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(authenticationController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"user\":{\"id\":0,\"first_name\":null,\"last_name\":null,\"email\":null,\"password\":null,\"phone_number\":null,"
                                        + "\"request_token\":null,\"authorities\":null,\"provider\":null,\"llmSelection\":null,\"enabled\":true,"
                                        + "\"credentialsNonExpired\":true,\"accountNonExpired\":true,\"username\":null,\"accountNonLocked\":true},\"jwt\":\"Invalid"
                                        + " email or password.\"}"));
    }

    /**
     * Method under test: {@link AuthenticationController#updateUser(UserReqAto)}
     */
    @Test
    void testUpdateUser() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

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
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange and Act
        ResponseEntity<?> actualUpdateUserResult = (new AuthenticationController()).updateUser(mock(UserReqAto.class));

        // Assert
        assertEquals("Failed to update user.", actualUpdateUserResult.getBody());
        assertEquals(500, actualUpdateUserResult.getStatusCodeValue());
        assertTrue(actualUpdateUserResult.getHeaders().isEmpty());
    }
}
