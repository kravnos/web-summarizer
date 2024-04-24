package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;

import java.util.HashSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.server.core.HeaderLinksResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {PasswordResetController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class PasswordResetControllerTest {
    @MockBean
    private AuthenticationController authenticationController;

    @MockBean
    private JavaMailSender javaMailSender;

    @Autowired
    private PasswordResetController passwordResetController;

    @MockBean
    private UserServiceImpl userServiceImpl;

    /**
     * Method under test: {@link PasswordResetController#code(String, Model)}
     */
    @Test
    void testCode() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/code")
                .param("login_email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(passwordResetController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email"))
                .andExpect(MockMvcResultMatchers.view().name("user/code"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/code"));
    }

    /**
     * Method under test: {@link PasswordResetController#send(String, Model)}
     */
    @Test
    void testSend() throws Exception {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<SimpleMailMessage>any());
        doNothing().when(userServiceImpl).setPasswordRequestToken(Mockito.<String>any(), Mockito.<User>any());
        when(userServiceImpl.loadUserByUsername(Mockito.<String>any())).thenReturn(new User("jane.doe@example.org"));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/send").param("code_email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(passwordResetController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email", "html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/reset"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/reset"));
    }

    /**
     * Method under test: {@link PasswordResetController#send(String, Model)}
     */
    @Test
    void testSend2() throws Exception {
        // Arrange
        doNothing().when(javaMailSender).send(Mockito.<SimpleMailMessage>any());
        doNothing().when(userServiceImpl).setPasswordRequestToken(Mockito.<String>any(), Mockito.<User>any());
        when(userServiceImpl.loadUserByUsername(Mockito.<String>any())).thenReturn(null);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/send").param("code_email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(passwordResetController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/code"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/code"));
    }

    /**
     * Method under test: {@link PasswordResetController#code(String, Model)}
     */
    @Test
    void testCode2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/code", "Uri Variables")
                .param("login_email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(passwordResetController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email"))
                .andExpect(MockMvcResultMatchers.view().name("user/code"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/code"));
    }

    /**
     * Method under test:
     * {@link PasswordResetController#reset(String, String, String, Model)}
     */
    @Test
    void testReset() throws Exception {
        // Arrange
        Mockito.<ResponseEntity<?>>when(authenticationController.registerUser(Mockito.<User>any()))
                .thenReturn(mock(HeaderLinksResponseEntity.class));

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
        doNothing().when(userServiceImpl).setPasswordRequestToken(Mockito.<String>any(), Mockito.<User>any());
        when(userServiceImpl.getUserByEmailAndResetToken(Mockito.<String>any(), Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/reset")
                .param("reset_code", "foo")
                .param("reset_email", "foo")
                .param("reset_password", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(passwordResetController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email", "html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }
}
