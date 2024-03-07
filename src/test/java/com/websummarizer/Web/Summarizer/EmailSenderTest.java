package com.websummarizer.Web.Summarizer;
import com.websummarizer.Web.Summarizer.controller.WebController;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(WebController.class)
public class EmailSenderTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void validEmail() throws Exception {
        String validEmail = "cosc4p02websummary@gmail.com";
        User user = new User();
        user.setEmail(validEmail);
        mvc.perform(MockMvcRequestBuilders.post("/employees"));
        //assertEquals(expected, result, "The parser did not return the expected result.");
    }
}
