package com.websummarizer.Web.Summarizer;
import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.WebController;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(WebController.class)
@WebMvcTest(WebController.class)
@AutoConfigureMockMvc
public class EmailSenderTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private Bart bart;
    @MockBean
    private UserServiceImpl userService;
    @MockBean
    private JavaMailSender emailSender;
    @MockBean
    @Value("${WEBADDRESS}")
    private String webaddress;

    private static final Logger logger = Logger.getLogger(Bart.class.getName());
    @Test
    public void testHTTPRequestForEmailResetPW() throws Exception {
        mvc.perform(post("/emailResetPW").contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void invalidEmailSyntax() throws Exception {
        //  Replace this email with another email of a user within your database
        String validEmail = "aaa";
        User user = new User();
        user.setEmail(validEmail);
        MvcResult temp = mvc.perform(post("/emailResetPW"))
                .andExpect((ResultMatcher) content().string("/redirect"))
                .andReturn();
    }
}
