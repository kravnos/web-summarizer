package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controller class for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/create")
    public ResponseEntity<User> registerUser(@ModelAttribute User user, @RequestParam(value = "email") String email, Model model) {
        logger.info("Received user creation request: " + user);
        boolean isRegistered;
        try {
            isRegistered = authenticationService.registerUser(user)!=null;
            logger.info("User registered successfully: " + user.getEmail());

            if (isRegistered) {
                logger.info("User registered successfully: " + user);
                model.addAttribute("isRegistered", true);
                model.addAttribute("message", "<span class=\"bi bi-check-circle-fill\"></span> User '" + email + "' created successfully. Please login.");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            logger.severe("Failed to register user: " + e.getMessage());
            model.addAttribute("isRegistered", false);
            model.addAttribute("message", "<span class=\"bi bi-exclamation-triangle-fill\"></span> Registration error for '" + email + "'. Please try again.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new User());
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param body UserDTO object containing login credentials.
     * @return The view name for login status.
     */
    @PostMapping("/login")
    public LoginResponseDTO loginUser(@ModelAttribute UserDTO body) {
        logger.info("User login request for : " + body.getLogin_email());
        try {
            LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getLogin_email(), body.getLogin_password());
            logger.info("User login successful: " + body.getLogin_email());
            return loginResponseDTO;
        } catch (Exception e) {
            logger.severe("Failed to login user: " + e.getMessage());
            throw e; // Rethrow the exception or handle as needed   //TODO
        }
    }

}