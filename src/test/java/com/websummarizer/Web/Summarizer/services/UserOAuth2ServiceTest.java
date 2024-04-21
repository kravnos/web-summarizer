package com.websummarizer.Web.Summarizer.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@ContextConfiguration(classes = {UserOAuth2Service.class})
@ExtendWith(SpringExtension.class)
class UserOAuth2ServiceTest {
    @Autowired
    private UserOAuth2Service userOAuth2Service;
    @MockBean
    private OAuth2User oAuth2User;

    /**
     * Method under test: {@link UserOAuth2Service#loadUser(OAuth2UserRequest)}
     */
    @Test
    void testLoadUser() throws OAuth2AuthenticationException{
        //  Mock a user request and return a mock OAuth2User for the method return object
        OAuth2UserRequest userRequest = mock(OAuth2UserRequest.class);
        doReturn(oAuth2User).when(userOAuth2Service).loadUser(userRequest);
        Assertions.assertNotNull(userOAuth2Service.loadUser(userRequest));

    }
}
