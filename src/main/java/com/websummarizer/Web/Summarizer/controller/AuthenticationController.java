package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controller class for handling authentication-related endpoints.
 */
@Controller
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint for creating a user.
     *
     * @param email The email of the user to be created.
     * @param user The User object containing user details.
     * @param model The Model object for adding attributes.
     * @return The view name for user creation status.
     */
    @PostMapping("/create")
    public String createUser(
            @RequestParam(value = "email") String email,
            @ModelAttribute User user,
            Model model
    ) {
        logger.info("Received user creation request: " + user);
        boolean isRegistered = false;

        try {
            isRegistered = authenticationService.registerUser(user) != null;
        } catch (Exception e) {
            logger.warning("User creation failed: " + e.getMessage());
        }

        if (isRegistered) {
            logger.info("User created successfully: " + user);
            model.addAttribute("isRegistered", true);
            model.addAttribute("message", "<span class=\"bi bi-check-circle-fill\"></span> User '" + email + "' created successfully. Please login.");
            return "user/login";
        } else {
            model.addAttribute("isRegistered", false);
            model.addAttribute("message", "<span class=\"bi bi-exclamation-triangle-fill\"></span> Registration error for '" + email + "'. Please try again.");
            return "user/register";
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param body UserDTO object containing login credentials.
     * @return The view name for login status.
     */
    @PostMapping("/login")
    public String loginUser(@ModelAttribute UserDTO body){
        logger.info("User login request for : "+body.getLogin_email()+ " password: "+body.getLogin_password());
        LoginResponseDTO loginResponseDTO = authenticationService.loginUser(body.getLogin_email(),body.getLogin_password());

        if(loginResponseDTO!=null && loginResponseDTO.getJwt()!=null){
            return "user/login";
        }else {
            return "redirect:/";
        }
    }

}