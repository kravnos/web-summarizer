package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.*;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service class for handling user authentication-related operations.
 */
@Service
@Transactional
public class OAuth2AuthenticationService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = Logger.getLogger(OAuth2AuthenticationService.class.getName());

    /**
     * Registers a new user.
     *
     * @param user The user to register.
     */
    public User registerUser(User user) {
        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        user.setAuthorities(authorities);

        logger.log(Level.INFO, "User registered successfully: {0}", user.getEmail());
        return userRepo.save(user);
    }

    public LoginResponseDTO processOAuthPostLoginGoogle(DefaultOidcUser oidcUser) {
        String email = oidcUser.getEmail();
        logger.info("received oauth 2 Google user creation request: " + email);
        User user = userRepo.findByEmail(email).orElse(null);
        //Process the request only when the user email is not already registered with the application
        if (user == null) {
            //todo choose whatever is necessary can extract photo as well
            String firstName = oidcUser.getGivenName();
            String lastName = oidcUser.getFamilyName();
            String phone = oidcUser.getPhoneNumber();
            //todo : retrieve user first and last name as well
            User oauthUser = new User(firstName, lastName, email, passwordEncoder.encode(email), phone, null,
                    "bart", Provider.GOOGLE);
            //todo: newUser.setEnabled(true);
            try {
                registerUser(oauthUser);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(email, email)
                );
                String token = tokenService.generateJwt(authentication);
                User loggedInUser = userRepo.findByEmail(email).orElse(null);
                logger.log(Level.INFO, "User login successful: {0}", email);

                return new LoginResponseDTO(loggedInUser, token);
            } catch (AuthenticationException e) {
                logger.log(Level.WARNING, "Failed to login user: {0}", email);
                return new LoginResponseDTO(null, "");
            }
        } else {
            //TODO : check what will happen if the user has a email registered and then tries to loging using google
            User user1 = userRepo.findByEmail(email).orElse(null);
            try {
                assert user1 != null;
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user1.getEmail(), email)
                );
                String token = tokenService.generateJwt(authentication);
                User loggedInUser = userRepo.findByEmail(email).orElse(null);
                logger.log(Level.INFO, "User login successful: {0}", email);

                return new LoginResponseDTO(loggedInUser, token);
            } catch (AuthenticationException e) {
                logger.log(Level.WARNING, "Failed to login user: {0}", email);
                return new LoginResponseDTO(null, "");
            }
        }
    }

    public LoginResponseDTO processOAuthPostLoginGithub(UserOAuth2 oauth2User) {
        String uniqueUserName = oauth2User.getLogin();
        logger.info("received oauth 2 GITHUB user creation request: " + uniqueUserName);
        User user = userRepo.findByEmail(uniqueUserName).orElse(null);

        if (uniqueUserName != null && user == null) {

            User oauthUser = new User(uniqueUserName, uniqueUserName, uniqueUserName, passwordEncoder.encode(uniqueUserName), null,
                    null, "bart", Provider.GITHUB);

            try{
                registerUser(oauthUser);
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(uniqueUserName, uniqueUserName)
                );
                String token = tokenService.generateJwt(authentication);
                User loggedInUser = userRepo.findByEmail(uniqueUserName).orElse(null);
                logger.log(Level.INFO, "User login successful: {0}", uniqueUserName);
                return new LoginResponseDTO(loggedInUser, token);
            }catch (AuthenticationException e) {
                logger.log(Level.WARNING, "Failed to login user: {0}", uniqueUserName);
                return new LoginResponseDTO(null, "");
            }




        } else {
            //TODO : check what will happen if the user has a email registered and then tries to loging using google
            User user1 = userRepo.findByEmail(uniqueUserName).orElse(null);
            try {
                assert user1 != null;
                Authentication authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(user1.getEmail(), uniqueUserName)
                );
                String token = tokenService.generateJwt(authentication);
                User loggedInUser = userRepo.findByEmail(uniqueUserName).orElse(null);
                logger.log(Level.INFO, "User login successful: {0}", uniqueUserName);

                return new LoginResponseDTO(loggedInUser, token);
            } catch (AuthenticationException e) {
                logger.log(Level.WARNING, "Failed to login user: {0}", uniqueUserName);
                return new LoginResponseDTO(null, "");
            }
        }
    }
}
