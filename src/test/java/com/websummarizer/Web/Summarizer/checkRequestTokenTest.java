package com.websummarizer.Web.Summarizer;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.WebController;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(WebController.class)
@AutoConfigureMockMvc(addFilters = false)   // Disallow SecurityConfig from messing up the tests

public class checkRequestTokenTest {
    //  Needed to mock the behaviour of this method
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

    //  Return "Fail" if the url is attempted to be reached with an invalid token value; with an invalid token, it will return null
    //  Status 352: Invalid Token / No user contains the token in the database
    @Test
    public void invalidRequestToken() throws Exception {
        String invalidToken= "aaa";
        String url = "/password-reset?token=" + invalidToken;
        mvc.perform(get(url)).andExpect(MockMvcResultMatchers.status().is(200));
    }

    //  Return "Pass" if a user was successfully pulled from the database
    //  Status 353: Successful redirect to password reset screen
    @Test
    public void validRequestToken() throws Exception {
        String validToken= "123456789";   // Pretend this is valid
        String url = "/password-reset?token=" + validToken;

        //  Return a new user. It will be empty, but at least it is not null
        when(userService.getUserByPasswordResetToken(validToken)).thenReturn(new User());
        mvc.perform(get(url)).andExpect(MockMvcResultMatchers.status().is(353));
    }
}
