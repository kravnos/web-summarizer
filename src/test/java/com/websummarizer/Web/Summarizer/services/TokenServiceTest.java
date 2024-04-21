package com.websummarizer.Web.Summarizer.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TokenService.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class TokenServiceTest {

    @MockBean
    private JwtDecoder jwtDecoder;
    @MockBean
    private JwtEncoder jwtEncoder;
    @MockBean
    private Jwt jwt;

    @Autowired
    private TokenService tokenService;

    /**
     * Method under test: {@link TokenService#generateJwt(Authentication)}
     */
    @Test
    void testGenerateJwt() throws JwtEncodingException {

        // Mock Authentication, JWT, and Token from mocked JWT
        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);

        when(authentication.getName()).thenReturn("User");
        when(jwtEncoder.encode(Mockito.any())).thenReturn(jwt);
        when(jwt.getTokenValue()).thenReturn("token");

        // Test that the method returns something that isn't null (i.e a String)
        Assertions.assertNotNull(tokenService.generateJwt(authentication));
        ;

    }
}
