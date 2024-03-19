package com.websummarizer.Web.Summarizer;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.WebController;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(WebController.class)
@AutoConfigureMockMvc(addFilters = false)   // Disallow SecurityConfig from messing up the tests
public class resetPWTest {

    @MockBean
    private Bart bart;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private JavaMailSender emailSender;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before("")
    public void setup()
    {
        //Init MockMvc Object and build
        /*
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
         */
    }


    //  Return "Fail" if the email address doesn't follow proper syntax
    //  Status 350: Invalid Email Syntax
    @Test
    public void invalidEmailSyntax() throws Exception {
        String invalidEmail = "aaa";
        mvc.perform(post("/emailResetPW")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", invalidEmail)
                        .secure(true))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    //  Continue if email syntax is valid but return "Fail" if the email address cannot be found
    //  Status 351: Email Not Found In Database
    @Test
    public void noEmailInDatabase() throws Exception {
        String validEmailSyntax = "aaa@aaa.com";  // Enter a syntactically correct email but one that isn't in your db
        mvc.perform(post("/emailResetPW")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", validEmailSyntax)
                        .secure(true))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }

    //  Return "Pass" when a valid email address matches one in the database
    //  Status 200: OK
    @Test
    public void emailSuccess() throws Exception {
        String validEmail = "real-email@email.com";
        User user = new User();
        user.setEmail(validEmail);

        //  When this method is called using any token string and any User object, return 1 to represent a Success
        when(userService.setPasswordRequestToken(anyString(), any(User.class))).thenReturn(1);
        //  The above method calls for setRequestToken(). To make sure it is correct, we have it accept any token string and an email that matches the valid email
        when(userRepo.setRequestToken(anyString(), eq(validEmail))).thenReturn(1);

        mvc.perform(post("/emailResetPW")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("email", validEmail)
                        .secure(true))
                .andExpect(MockMvcResultMatchers.status().is(200));
    }
}
