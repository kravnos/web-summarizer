package com.websummarizer.Web.Summarizer.services;

import com.websummarizer.Web.Summarizer.controller.AuthenticationController;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.Role;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.RoleRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import org.apache.commons.logging.Log;
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
    public User registerUser(User user) {
        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assuming you have a default role for users
        Role userRole = roleRepo.findByAuthority("USER")
                .orElseThrow(() -> new IllegalStateException("Default role not found"));

        Set<Role> authorities = new HashSet<>();
        authorities.add(userRole);

        user.setAuthorities(authorities);

        return userRepo.save(user);
    }

    public LoginResponseDTO loginUser(String email, String password){
        try {

            logger.info("in loginUser method in authentication service class" + email+ " "+password);
            logger.info("everything fine upto 0");
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            logger.info("everything fine upto 1");
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
