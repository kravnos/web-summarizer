package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
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
     * Registers a new user.
     *
     * @param user The user to register.
     * @return The registered user.
     */
    public User registerUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        user.setAuthorities(authorities);

        return userRepo.save(user);
    }

    /**
     * Logs in a user.
     *
     * @param email The email of the user to log in.
     * @param password The password of the user to log in.
     * @return LoginResponseDTO containing user information and JWT token.
     */
    public LoginResponseDTO loginUser(String email, String password){
        try {

            logger.info("in loginUser method in authentication service class" + email+ " "+password);
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            String token = tokenService.generateJwt(authentication);
            logger.info("everything fine upto 2");
            LoginResponseDTO loginResponseDTO = new LoginResponseDTO(userRepo.findByEmail(email).get(), token);
            logger.info("returning the following login response dto: "+loginResponseDTO);
            return loginResponseDTO;
        }catch (AuthenticationException e){
            logger.info("failed loginUser method in authentication service class running catch");
            return new LoginResponseDTO(null,"");
        }
    }
}