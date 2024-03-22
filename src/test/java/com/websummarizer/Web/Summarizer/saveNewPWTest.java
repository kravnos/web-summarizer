package com.websummarizer.Web.Summarizer;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.PasswordResetController;
import com.websummarizer.Web.Summarizer.controller.WebController;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Enumeration;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(PasswordResetController.class)
@AutoConfigureMockMvc(addFilters = false)   // Disallow SecurityConfig from messing up the tests
public class saveNewPWTest {
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


    //  Tests to make sure that this method successfully redirects the user to the homepage upon completion
    @Test
    public void saveNewPWSuccess() throws Exception {
        // Construct temp params for the method
        User user1 = new User();    //  Represents the new password
        user1.setPassword("test");
        User user2 = new User();    // Represents the user whose account pw is being changed
        user2.setPassword("old");
        HttpSession session = createSession();  // Represents HttpSession containing the user's account pw to be changed
        session.setAttribute("user", user1);

        mvc.perform(post("/saveNewPW")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .sessionAttr("user", user1)
                        .flashAttr("user", user2)
                        .secure(true))
                .andExpect(MockMvcResultMatchers.status().is(302));
    }

    public HttpSession createSession(){
        return new HttpSession() {
            @Override
            public long getCreationTime() {
                return 0;
            }

            @Override
            public String getId() {
                return null;
            }

            @Override
            public long getLastAccessedTime() {
                return 0;
            }

            @Override
            public ServletContext getServletContext() {
                return null;
            }

            @Override
            public void setMaxInactiveInterval(int i) {

            }

            @Override
            public int getMaxInactiveInterval() {
                return 0;
            }

            @Override
            public Object getAttribute(String s) {
                return null;
            }

            @Override
            public Enumeration<String> getAttributeNames() {
                return null;
            }

            @Override
            public void setAttribute(String s, Object o) {

            }

            @Override
            public void removeAttribute(String s) {

            }

            @Override
            public void invalidate() {

            }

            @Override
            public boolean isNew() {
                return false;
            }
        };
    }
}
