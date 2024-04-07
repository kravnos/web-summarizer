package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;

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

    public User processOAuthPostLoginGoogle(DefaultOidcUser oidcUser){
        String email = oidcUser.getEmail();
        logger.info("Received oauth 2 user creation request: " + email);
        User user = userRepo.findByEmail(email).orElse(null);
        //Process the request only when the user email is not already registered with the application
        if(user==null){
            //todo choose whatever is necessary can extract photo as well
            String firstName = oidcUser.getGivenName();
            String lastName = oidcUser.getFamilyName();
            String phone = oidcUser.getPhoneNumber();
            //todo : retrieve user first and last name as well
            User oauthUser = new User(firstName,lastName,email,null,phone,null,Provider.GOOGLE);
            //todo: newUser.setEnabled(true);
            return registerUser(oauthUser);
        }
        else {
            //TODO : check what will happen if the user has a email registered and then tries to loging using google
            return null;
        }
    }

}
