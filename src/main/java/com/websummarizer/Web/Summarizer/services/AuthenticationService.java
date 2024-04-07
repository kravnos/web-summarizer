package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.Provider;
import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
public class AuthenticationService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());

    /**
     * Registers a new user when user registers with their email and not service providers.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    public User registerUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setProvider(Provider.LOCAL);

        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        user.setAuthorities(authorities);

        logger.log(Level.INFO, "User registered successfully: {0}", user.getEmail());
        return userRepo.save(user);
    }

    /**
     * Logs in a user.
     *
     * @param email    The email of the user to log in.
     * @param password The password of the user to log in.
     * @return LoginResponseDTO containing user information and JWT token.
     */
    public LoginResponseDTO loginUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
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
