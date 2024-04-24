package com.websummarizer.Web.Summarizer.configs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.websummarizer.Web.Summarizer.services.OAuth2AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {SuccessHandler.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class SuccessHandlerTest {
    @MockBean
    private OAuth2AuthenticationService oAuth2AuthenticationService;

    @Autowired
    private SuccessHandler successHandler;

    /**
     * Method under test:
     * {@link SuccessHandler#onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, FilterChain, Authentication)}
     */
    @Test
    void testOnAuthenticationSuccess() throws ServletException, IOException {
        // Arrange
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        FilterChain chain = mock(FilterChain.class);
        doNothing().when(chain).doFilter(Mockito.<ServletRequest>any(), Mockito.<ServletResponse>any());

        // Act
        successHandler.onAuthenticationSuccess(request, response, chain, new BearerTokenAuthenticationToken("ABC123"));

        // Assert
        verify(chain).doFilter(isA(ServletRequest.class), isA(ServletResponse.class));
        assertEquals("/", response.getRedirectedUrl());
        assertEquals(1, response.getHeaderNames().size());
        assertEquals(302, response.getStatus());
        assertTrue(response.isCommitted());
    }

    /**
     * Method under test:
     * {@link SuccessHandler#onAuthenticationSuccess(HttpServletRequest, HttpServletResponse, Authentication)}
     */
    @Test
    void testOnAuthenticationSuccess2() throws IOException {
        // Arrange
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getContextPath()).thenReturn("https://example.org/example");
        MockHttpServletResponse response = new MockHttpServletResponse();

        // Act
        successHandler.onAuthenticationSuccess(request, response, new BearerTokenAuthenticationToken("ABC123"));

        // Assert
        verify(request).getContextPath();
        assertEquals("https://example.org/example/", response.getRedirectedUrl());
        assertEquals(1, response.getHeaderNames().size());
        assertEquals(302, response.getStatus());
        assertTrue(response.isCommitted());
    }
}
