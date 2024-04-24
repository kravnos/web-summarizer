package com.websummarizer.Web.Summarizer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.llmConnectors.Bart;
import com.websummarizer.Web.Summarizer.llmConnectors.OpenAi;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.UserReqAto;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;

@ContextConfiguration(classes = {WebController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class WebControllerTest {
    @MockBean
    private AuthenticationController authenticationController;

    @MockBean
    private Bart bart;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private OpenAi openAi;

    @MockBean
    private UserServiceImpl userServiceImpl;

    @Autowired
    private WebController webController;


    /**
     * Method under test:
     * {@link WebController#account(String, String, String, String, UserReqAto, HttpServletRequest, Model)}
     */
    @Test
    void testAccount() {
        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");

        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));
        UserReqAto user = UserReqAto.builder()
                .account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .provider(Provider.LOCAL)
                .request_token("ABC123")
                .build();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertEquals("user/account", webController.account("jane.doe@example.org", "Llm", "Is Logged In", "Is Pro User",
                user, request, new ConcurrentModel()));
    }

    /**
     * Method under test:
     * {@link WebController#account(String, String, String, String, UserReqAto, HttpServletRequest, Model)}
     */
    @Test
    void testAccount2() {
        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");

        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));
        UserReqAto user = UserReqAto.builder()
                .account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .provider(Provider.LOCAL)
                .request_token("ABC123")
                .build();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertEquals("user/account",
                webController.account(null, "Llm", "Is Logged In", "Is Pro User", user, request, new ConcurrentModel()));
    }

    /**
     * Method under test:
     * {@link WebController#account(String, String, String, String, UserReqAto, HttpServletRequest, Model)}
     */
    @Test
    void testAccount3() {
        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");

        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));
        UserReqAto user = UserReqAto.builder()
                .account_llm("3")
                .email("jane.doe@example.org")
                .first_name("Jane")
                .last_name("Doe")
                .password("iloveyou")
                .phone_number("6625550144")
                .provider(Provider.LOCAL)
                .request_token("ABC123")
                .build();
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertEquals("user/account",
                webController.account("", "Llm", "Is Logged In", "Is Pro User", user, request, new ConcurrentModel()));
    }

    /**
     * Method under test:
     * {@link WebController#createUser(String, String, User, HttpServletRequest, Model)}
     */
    @Test
    void testCreateUser() {
        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");

        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));

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
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertEquals("user/register",
                webController.createUser("Jane", "jane.doe@example.org", user, request, new ConcurrentModel()));
    }

    /**
     * Method under test:
     * {@link WebController#createUser(String, String, User, HttpServletRequest, Model)}
     */
    @Test
    void testCreateUser2() {
        // Arrange
        Bart bart = mock(Bart.class);
        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));

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
        MockHttpServletRequest request = new MockHttpServletRequest();

        // Act and Assert
        assertEquals("user/register",
                webController.createUser("Jane", "jane.doe@example.org", user, request, new ConcurrentModel()));
    }

    /**
     * Method under test: {@link WebController#checkPassword(String)}
     */
    @Test
    void testCheckPassword() {
        // Arrange, Act and Assert
        assertFalse(webController.checkPassword("iloveyou"));
    }

    /**
     * Method under test: {@link WebController#cancel(Model)}
     */
    @Test
    void testCancel() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/cancel");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/cancel"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/cancel"));
    }

    /**
     * Method under test: {@link WebController#cancel(Model)}
     */
    @Test
    void testCancel2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/cancel");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/cancel"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/cancel"));
    }

    /**
     * Method under test:
     * {@link WebController#getSummary(String, String, String, HttpServletRequest, HttpSession, Model)}
     */
    @Test
    void testGetSummary() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/summary")
                .param("input", "foo")
                .param("isLoggedIn", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(6))
                .andExpect(MockMvcResultMatchers.model().attributeExists("date", "input", "output", "url", "user"))
                .andExpect(MockMvcResultMatchers.view().name("api/summary"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/summary"));
    }

    /**
     * Method under test:
     * {@link WebController#login(String, String, String, HttpServletRequest, Model)}
     */
    @Test
    void testLogin() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }

    /**
     * Method under test:
     * {@link WebController#login(String, String, String, HttpServletRequest, Model)}
     */
    @Test
    void testLogin2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/login")
                .param("isLoggedIn", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }

    /**
     * Method under test:
     * {@link WebController#login(String, String, String, HttpServletRequest, Model)}
     */
    @Test
    void testLogin3() throws Exception {
        // Arrange
        when(historyService.findHistoryId(anyLong())).thenReturn(new ArrayList<>());

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
        when(userServiceImpl.getFoundUser(Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString());

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model().attributeExists("histories", "isLoggedIn"))
                .andExpect(MockMvcResultMatchers.view().name("user/account"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/account"));
    }

    /**
     * Method under test:
     * {@link WebController#login(String, String, String, HttpServletRequest, Model)}
     */
    @Test
    void testLogin4() throws Exception {
        // Arrange
        when(historyService.findHistoryId(anyLong())).thenReturn(new ArrayList<>());

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
        when(userServiceImpl.getFoundUser(Mockito.<String>any())).thenReturn(user);
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("path", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(MockMvcResultMatchers.model().attributeExists("histories", "isLoggedIn"))
                .andExpect(MockMvcResultMatchers.view().name("user/account"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/account"));
    }

    /**
     * Method under test: {@link WebController#newChat()}
     */
    @Test
    void testNewChat() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/newchat");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("api/newchat"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/newchat"));
    }

    /**
     * Method under test: {@link WebController#newChat()}
     */
    @Test
    void testNewChat2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/newchat");
        requestBuilder.contentType("https://example.org/example");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("api/newchat"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("api/newchat"));
    }

    /**
     * Method under test: {@link WebController#pro(String, String, Model)}
     */
    @Test
    void testPro() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/pro");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }

    /**
     * Method under test: {@link WebController#pro(String, String, Model)}
     */
    @Test
    void testPro2() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/pro").param("isLoggedIn", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(3))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/login"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/login"));
    }

    /**
     * Method under test: {@link WebController#pro(String, String, Model)}
     */
    @Test
    void testPro3() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/pro");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString());

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/pro"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/pro"));
    }

    /**
     * Method under test: {@link WebController#pro(String, String, Model)}
     */
    @Test
    void testPro4() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/pro");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("isProUser", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(0))
                .andExpect(MockMvcResultMatchers.view().name("user/pro"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/pro"));
    }

    /**
     * Method under test: {@link WebController#pro(String, String, Model)}
     */
    @Test
    void testPro5() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/pro");
        MockHttpServletRequestBuilder paramResult = postResult.param("isLoggedIn", Boolean.TRUE.toString());
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("isProUser", Boolean.TRUE.toString());

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("isProUser"))
                .andExpect(MockMvcResultMatchers.view().name("user/thankyou"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/thankyou"));
    }

    /**
     * Method under test: {@link WebController#purchase(String, Model)}
     */
    @Test
    void testPurchase() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/purchase")
                .param("isLoggedIn", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(5))
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("html", "isLoggedIn", "isProUser", "isValid", "message"))
                .andExpect(MockMvcResultMatchers.view().name("user/thankyou"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/thankyou"));
    }

    /**
     * Method under test: {@link WebController#register(String, Model)}
     */
    @Test
    void testRegister() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/register")
                .param("login_email", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.model().attributeExists("email"))
                .andExpect(MockMvcResultMatchers.view().name("user/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/register"));
    }
}
