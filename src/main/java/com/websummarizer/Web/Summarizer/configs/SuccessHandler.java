package com.websummarizer.Web.Summarizer.configs;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.UserOAuth2;
import com.websummarizer.Web.Summarizer.services.OAuth2AuthenticationService;
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

/**
 * Custom authentication success handler to handle successful OAuth2 authentication.
 */
@Component
public class SuccessHandler implements AuthenticationSuccessHandler {

    private static final Logger logger = Logger.getLogger(SuccessHandler.class.getName());

    @Autowired
    OAuth2AuthenticationService oAuth2AuthenticationService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        // Log the authentication details
        logger.info("Authentication of OAuth2 user request: " + authentication);

        // Check the type of OAuth2 user and process accordingly
        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();
            LoginResponseDTO loginResponseDTO = oAuth2AuthenticationService.processOAuthPostLoginGoogle(oidcUser);
            logger.info("User after processOAuthPostLoginGoogle: " + loginResponseDTO);
            // Set session attributes for the user
            request.getSession().setAttribute("username", loginResponseDTO.getUser().getEmail());
            request.getSession().setAttribute("email", loginResponseDTO.getUser().getEmail());
            request.getSession().setAttribute("jwt", loginResponseDTO.getJwt());
            logger.info("Created following user at on success method: " + loginResponseDTO);
        } else if (authentication.getPrincipal() instanceof UserOAuth2) {
            UserOAuth2 oauth2User = (UserOAuth2) authentication.getPrincipal();
            LoginResponseDTO loginResponseDTO = oAuth2AuthenticationService.processOAuthPostLoginGithub(oauth2User);
            logger.info("User after processOAuthPostLoginGithub: " + loginResponseDTO);
            // Set session attributes for the user
            request.getSession().setAttribute("username", loginResponseDTO.getUser().getEmail());
            request.getSession().setAttribute("email", loginResponseDTO.getUser().getEmail());
            request.getSession().setAttribute("jwt", loginResponseDTO.getJwt());
            logger.info("Created following user at on success method: " + loginResponseDTO.getUser());
        }

        // Redirect the user to the home page
        new DefaultRedirectStrategy().sendRedirect(request, response, "/");
    }
}
