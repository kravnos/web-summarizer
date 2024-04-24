package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerDiffblueTest {
    @MockBean
    private HistoryService historyService;

    @MockBean
    private ShortLinkGenerator shortLinkGenerator;

    @Autowired
    private UserController userController;

    @MockBean
    private UserRepo userRepo;

    /**
     * Method under test: {@link UserController#addNewHistory(String, String)}
     */
    @Test
    void testAddNewHistory() throws Exception {
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

        History history = new History();
        history.setHistoryContent("Not all who wander are lost");
        history.setId(1L);
        history.setShort_link("Short link");
        history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        history.setUser(user);
        when(historyService.save(Mockito.<History>any())).thenReturn(history);
        when(shortLinkGenerator.generateShortUrl()).thenReturn("https://example.org/example");

        User user2 = new User();
        user2.setAuthorities(new HashSet<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(1L);
        user2.setLast_name("Doe");
        user2.setLlmSelection("Llm Selection");
        user2.setPassword("iloveyou");
        user2.setPhone_number("6625550144");
        user2.setProvider(Provider.LOCAL);
        user2.setRequest_token("ABC123");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/add-new-history")
                .param("email", "foo")
                .param("output", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"historyContent\":\"Not all who wander are lost\",\"short_link\":\"Short link\",\"uploadTime\":[1970,1"
                                        + ",1,0,0],\"user\":{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password"
                                        + "\":\"iloveyou\",\"phone_number\":\"6625550144\",\"request_token\":\"ABC123\",\"authorities\":[],\"provider\":\"LOCAL"
                                        + "\",\"llmSelection\":\"Llm Selection\",\"enabled\":true,\"username\":\"jane.doe@example.org\",\"accountNonLocked\""
                                        + ":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true}}"));
    }

    /**
     * Method under test: {@link UserController#getAuthenticatedUserEmail()}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testGetAuthenticatedUserEmail() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.security.core.Authentication.getName()" because the return value of "org.springframework.security.core.context.SecurityContext.getAuthentication()" is null
        //       at com.websummarizer.Web.Summarizer.controller.UserController.getAuthenticatedUserEmail(UserController.java:33)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:564)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange and Act
        (new UserController()).getAuthenticatedUserEmail();
    }

    /**
     * Method under test: {@link UserController#addToPreviousHistory(String)}
     */
    @Test
    void testAddToPreviousHistory() throws Exception {
        // Arrange
        when(historyService.findAllHistory(anyLong())).thenReturn(new ArrayList<>());

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
        Optional<User> ofResult = Optional.of(user);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/histories").param("email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test:
     * {@link UserController#addToPreviousHistory(String, String, String)}
     */
    @Test
    void testAddToPreviousHistory2() throws Exception {
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

        History history = new History();
        history.setHistoryContent("Not all who wander are lost");
        history.setId(1L);
        history.setShort_link("Short link");
        history.setUploadTime(LocalDate.of(1970, 1, 1).atStartOfDay());
        history.setUser(user);
        when(historyService.save(Mockito.<History>any())).thenReturn(history);

        User user2 = new User();
        user2.setAuthorities(new HashSet<>());
        user2.setEmail("jane.doe@example.org");
        user2.setFirst_name("Jane");
        user2.setId(1L);
        user2.setLast_name("Doe");
        user2.setLlmSelection("Llm Selection");
        user2.setPassword("iloveyou");
        user2.setPhone_number("6625550144");
        user2.setProvider(Provider.LOCAL);
        user2.setRequest_token("ABC123");
        Optional<User> ofResult = Optional.of(user2);
        when(userRepo.findByEmail(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/users/{short_link}/append-history", "Short link")
                .param("email", "foo")
                .param("output", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"historyContent\":\"Not all who wander are lost\",\"short_link\":\"Short link\",\"uploadTime\":[1970,1"
                                        + ",1,0,0],\"user\":{\"id\":1,\"first_name\":\"Jane\",\"last_name\":\"Doe\",\"email\":\"jane.doe@example.org\",\"password"
                                        + "\":\"iloveyou\",\"phone_number\":\"6625550144\",\"request_token\":\"ABC123\",\"authorities\":[],\"provider\":\"LOCAL"
                                        + "\",\"llmSelection\":\"Llm Selection\",\"enabled\":true,\"username\":\"jane.doe@example.org\",\"accountNonLocked\""
                                        + ":true,\"credentialsNonExpired\":true,\"accountNonExpired\":true}}"));
    }
}
