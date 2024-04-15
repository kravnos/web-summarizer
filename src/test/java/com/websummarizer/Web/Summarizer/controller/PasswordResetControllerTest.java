package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@WebMvcTest(PasswordResetController.class)
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
public class PasswordResetControllerTest {
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private AuthenticationService authenticationService;
    @MockBean
    private RoleRepo roleRepo;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private PasswordEncoder passwordEncoder;
    @MockBean
    private JavaMailSender emailSender;

    @Autowired
    private PasswordResetController controller;

    /**
     * Method under test: {@link PasswordResetController#code(String, Model)}
     */
    @Test
    public void codeTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/code");
        requestBuilder.param("login_email", "test@email.com");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/code"));
    }

    /**
     * Method under test: {@link PasswordResetController#send(String, Model, HttpServletResponse)}
     */
    @Test
    public void sendTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/send");
        requestBuilder.param("code_email", "test@email.com");

        //  Return "valid" user for testing purposes
        User user = new User();
        when(userService.loadUserByUsername(anyString())).thenReturn(user);

        //  Assert that the user has been brought to the password reset modal
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/reset"));

        // Assert that the set user reset token and email sending functionality worked
        verify(userService).setPasswordRequestToken(anyString(), any(User.class));
        verify(emailSender).send(any(SimpleMailMessage.class));
    }

    /**
     * Method under test: {@link PasswordResetController#reset(String, String, String, Model, HttpServletResponse)}
     */
    @Test
    public void resetTest() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/reset");
        requestBuilder.param("reset_email", "test@email.com");
        requestBuilder.param("reset_password", "123");
        requestBuilder.param("reset_code", "ABCDEF");

        //  Return "valid" user for testing purposes
        User user = new User();
        when(userService.getUserByEmailAndResetToken(anyString(),anyString())).thenReturn(user);

        //  Assert that the user has been brought back to the login page
        MockMvcBuilders.standaloneSetup(controller)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("user/login"));

        /*
            Assert that the following functionalities were called:
                -Changing the user password
                -Removing the reset token after a successful password change
         */
        verify(authenticationService).registerUser(any(User.class));
        verify(userService).setPasswordRequestToken(eq(null), any(User.class));
    }
}
