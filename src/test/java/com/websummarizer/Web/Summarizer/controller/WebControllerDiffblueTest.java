package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {WebController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WebControllerDiffblueTest {
    @MockBean
    private Bart bart;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private WebController webController;

    /**
     * Method under test: {@link WebController#createUser(User, HttpSession)}
     */
    @Test
    void testCreateUser() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirst_name("Jane");
        user.setId(1L);
        user.setLast_name("Doe");
        user.setPassword("iloveyou");
        user.setPhone_number("6625550144");
        when(userServiceImpl.createUser(Mockito.<User>any())).thenReturn(user);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/createUser");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/"))
                .andExpect(MockMvcResultMatchers.redirectedUrl("/"));
    }

    /**
     * Method under test: {@link WebController#getSummary(String, Model)}
     */
    @Test
    void testGetSummary() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/summary").param("input", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(7))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("date", "email", "fb", "input", "output", "twitter", "user"))
                .andExpect(MockMvcResultMatchers.view().name("api/summary"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/summary"));
    }

    /**
     * Method under test: {@link WebController#getSummary(String, Model)}
     */
    @Test
    void testGetSummary2() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/summary")
                .param("input", "https://example.org/example", "yyyy/MM/dd h:mm:ss a");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(7))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("date", "email", "fb", "input", "output", "twitter", "user"))
                .andExpect(MockMvcResultMatchers.view().name("api/summary"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/summary"));
    }

    /**
     * Method under test: {@link WebController#register()}
     */
    @Test
    void testRegister() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/register");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test: {@link WebController#register()}
     */
    @Test
    void testRegister2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/register");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test: {@link WebController#signIn()}
     */
    @Test
    void testSignIn() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/signin");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }

    /**
     * Method under test: {@link WebController#signIn()}
     */
    @Test
    void testSignIn2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/signin");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("index"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("index"));
    }
}
