package com.websummarizer.Web.Summarizer.configs;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserOAuth2;
import com.websummarizer.Web.Summarizer.services.OAuth2AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(SuccessHandler.class.getName());

    @Autowired
    OAuth2AuthenticationService oAuth2AuthenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException { //todo take care of exception
        logger.info("Authentication of oauth 2 user request: "+ authentication);
        if(authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            User user = oAuth2AuthenticationService.processOAuthPostLoginGoogle(oidcUser);
            logger.info("created following user at on success method: " + user);
        }else if (authentication.getPrincipal() instanceof UserOAuth2){
            UserOAuth2 oauth2User = (UserOAuth2) authentication.getPrincipal();
            User user = oAuth2AuthenticationService.processOAuthPostLoginGithub(oauth2User);
            logger.info("created following user at on success method: " + user);
        }
        new DefaultRedirectStrategy().sendRedirect(request,response,"/");
    }
}

