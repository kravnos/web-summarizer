package com.websummarizer.Web.Summarizer.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.websummarizer.Web.Summarizer.controller.shortlink.Shortlink;
import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.llmConnectors.Bart;
import com.websummarizer.Web.Summarizer.llmConnectors.OpenAi;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.HeaderLinksResponseEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
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
class WebControllerDiffblueTest {
    @MockBean
    private AuthenticationController authenticationController;

    @MockBean
    private Bart bart;

    @MockBean
    private BitLyController bitLyController;

    @MockBean
    private OpenAi openAi;

    @MockBean
    private Shortlink shortlink;

    @Autowired
    private WebController webController;

    /**
     * Method under test:
     * {@link WebController#authUser(String, String, String, String, UserDTO, Model, HttpServletRequest)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAuthUser() throws Exception {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: java.lang.NullPointerException: Cannot invoke "org.springframework.http.HttpStatusCode.is2xxSuccessful()" because the return value of "org.springframework.http.ResponseEntity.getStatusCode()" is null
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: Cannot invoke "org.springframework.http.HttpStatusCode.is2xxSuccessful()" because the return value of "org.springframework.http.ResponseEntity.getStatusCode()" is null
        //       at com.websummarizer.Web.Summarizer.controller.WebController.authUser(WebController.java:182)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.


        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");
        OpenAi openAi = new OpenAi("https://example.org/example", "ABC123", "Model");

        WebController webController = new WebController(bart, openAi);
        UserDTO userDTO = new UserDTO("jane.doe@example.org", "iloveyou");

        ConcurrentModel model = new ConcurrentModel();

        // Act
        webController.authUser("jane.doe@example.org", "iloveyou", "Is Pro User", "Path", userDTO, model,
                new MockHttpServletRequest());


    }

    /**
     * Method under test:
     * {@link WebController#account(String, String, String, UserReqAto, Model)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testAccount() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   jakarta.servlet.ServletException: Request processing failed: org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.websummarizer.Web.Summarizer.controller.user.UserReqAto]: Constructor threw exception
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   org.springframework.beans.BeanInstantiationException: Failed to instantiate [com.websummarizer.Web.Summarizer.controller.user.UserReqAto]: Constructor threw exception
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   java.lang.NullPointerException: first_name is marked non-null but is null
        //       at com.websummarizer.Web.Summarizer.controller.user.UserReqAto.<init>(UserReqAto.java:8)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:590)
        //       at jakarta.servlet.http.HttpServlet.service(HttpServlet.java:658)
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        Bart bart = new Bart("https://example.org/example", "ABC123");

        WebController webController = new WebController(bart, new OpenAi("https://example.org/example", "ABC123", "Model"));

        // Act
        webController.account("jane.doe@example.org", "Is Logged In", "Is Pro User", null, new ConcurrentModel());
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
     * {@link WebController#createUser(String, String, User, Model)}
     */
    @Test
    void testCreateUser() throws Exception {
        // Arrange
        HeaderLinksResponseEntity<RepresentationModel<?>> headerLinksResponseEntity = mock(HeaderLinksResponseEntity.class);
        when(headerLinksResponseEntity.getStatusCode()).thenReturn(null);
        Mockito.<ResponseEntity<?>>when(authenticationController.registerUser(Mockito.<User>any()))
                .thenReturn(headerLinksResponseEntity);
        when(shortlink.checkPassword(Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .param("email", "foo")
                .param("first_name", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/register"));
    }

    /**
     * Method under test:
     * {@link WebController#createUser(String, String, User, Model)}
     */
    @Test
    void testCreateUser2() throws Exception {
        // Arrange
        Mockito.<ResponseEntity<?>>when(authenticationController.registerUser(Mockito.<User>any())).thenReturn(null);
        when(shortlink.checkPassword(Mockito.<String>any())).thenReturn(true);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .param("email", "foo")
                .param("first_name", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/register"));
    }

    /**
     * Method under test:
     * {@link WebController#createUser(String, String, User, Model)}
     */
    @Test
    void testCreateUser3() throws Exception {
        // Arrange
        HeaderLinksResponseEntity<RepresentationModel<?>> headerLinksResponseEntity = mock(HeaderLinksResponseEntity.class);
        when(headerLinksResponseEntity.getStatusCode()).thenReturn(null);
        Mockito.<ResponseEntity<?>>when(authenticationController.registerUser(Mockito.<User>any()))
                .thenReturn(headerLinksResponseEntity);
        when(shortlink.checkPassword(Mockito.<String>any())).thenReturn(false);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create")
                .param("email", "foo")
                .param("first_name", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(4))
                .andExpect(MockMvcResultMatchers.model().attributeExists("html", "isValid", "message", "user"))
                .andExpect(MockMvcResultMatchers.view().name("user/register"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/register"));
    }

    /**
     * Method under test:
     * {@link WebController#getSummary(String, String, String, String, HttpSession, Model)}
     */
    @Test
    void testGetSummary() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        when(shortlink.Shortlink(Mockito.<String>any(), Mockito.<String>any(), Mockito.<HttpSession>any()))
                .thenReturn("Shortlink");
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
     * Method under test:
     * {@link WebController#getSummary(String, String, String, String, HttpSession, Model)}
     */
    @Test
    void testGetSummary2() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        when(shortlink.Shortlink(Mockito.<String>any(), Mockito.<String>any(), Mockito.<HttpSession>any()))
                .thenReturn("Shortlink");
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/summary")
                .param("input", "foo")
                .param("first_name", "foo");

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
     * Method under test:
     * {@link WebController#getSummary(String, String, String, String, HttpSession, Model)}
     */
    @Test
    void testGetSummary3() throws Exception {
        // Arrange
        when(bart.queryModel(Mockito.<String>any())).thenReturn("Query Model");
        when(shortlink.Shortlink(Mockito.<String>any(), Mockito.<String>any(), Mockito.<HttpSession>any()))
                .thenReturn("Shortlink");
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
     * Method under test: {@link WebController#login(String, String, String, Model)}
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
     * Method under test: {@link WebController#login(String, String, String, Model)}
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
     * Method under test: {@link WebController#login(String, String, String, Model)}
     */
    @Test
    void testLogin3() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString());

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("isLoggedIn"))
                .andExpect(MockMvcResultMatchers.view().name("user/account"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/account"));
    }

    /**
     * Method under test: {@link WebController#login(String, String, String, Model)}
     */
    @Test
    void testLogin4() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("path", "foo");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(webController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().size(2))
                .andExpect(MockMvcResultMatchers.model().attributeExists("isLoggedIn"))
                .andExpect(MockMvcResultMatchers.view().name("user/account"))
                .andExpect(MockMvcResultMatchers.forwardedUrl("user/account"));
    }

    /**
     * Method under test: {@link WebController#login(String, String, String, Model)}
     */
    @Test
    void testLogin5() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("path", "pro");

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
     * Method under test: {@link WebController#login(String, String, String, Model)}
     */
    @Test
    void testLogin6() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder requestBuilder = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("path", "pro")
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
     * Method under test: {@link WebController#login(String, String, String, Model)}
     */
    @Test
    void testLogin7() throws Exception {
        // Arrange
        MockHttpServletRequestBuilder postResult = MockMvcRequestBuilders.post("/user/login");
        MockHttpServletRequestBuilder paramResult = postResult.param("isLoggedIn", Boolean.TRUE.toString())
                .param("path", "pro");
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
