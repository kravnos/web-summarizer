package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.LoginDTO;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint for creating a user.
     *
     * @param user The user to create.
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
            //redirectAttributes.addFlashAttribute("success", "User '" + email + "' created successfully.");
            model.addAttribute("isRegistered", true);
            model.addAttribute("message", "<span class=\"bi bi-check-circle-fill\"></span> User '" + email + "' created successfully. Please login.");
            return "user/login";
        } else {
            //redirectAttributes.addFlashAttribute("error", "Registration for '" + email + "' failed.");
            model.addAttribute("isRegistered", false);
            model.addAttribute("message", "<span class=\"bi bi-exclamation-triangle-fill\"></span> Registration error for '" + email + "'. Please try again.");
            return "user/register";
        }
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody LoginDTO loginDTO) {
        logger.info("Received user login request: " + loginDTO);
        boolean isLoggedIn = false;

        try {
            isLoggedIn = authenticationService.loginUser(loginDTO.getUsername(), loginDTO.getPassword()) != null;
        } catch (Exception e) {
            logger.warning("User login failed: " + e.getMessage());
        }

        if (isLoggedIn) {
            logger.info("User created successfully: " + loginDTO);
            //redirectAttributes.addFlashAttribute("success", "User '" + email + "' created successfully.");
            //model.addAttribute("isRegistered", true);
            //model.addAttribute("message", "<span class=\"bi bi-check-circle-fill\"></span> User '" + email + "' created successfully. Please login.");
            return "user/login";
        } else {
            //redirectAttributes.addFlashAttribute("error", "Registration for '" + email + "' failed.");
            //model.addAttribute("isRegistered", false);
            // model.addAttribute("message", "<span class=\"bi bi-exclamation-triangle-fill\"></span> Registration error for '" + email + "'. Please try again.");
            return "user/register";
        }
    }

}
