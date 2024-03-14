package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

/**
 * Controller for web-related actions.
 */
@Controller
public class WebController {

    @Autowired
    private final Bart bart;

    @Autowired
    private UserServiceImpl userService;
    private static final Logger logger = Logger.getLogger(Bart.class.getName());

    /**
     * Constructor for WebController.
     *
     * @param bart The Bart instance to use.
     */
    public WebController(Bart bart) {
        this.bart = bart;
    }

    /**
     * Endpoint for user registration.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/register")
    public String register() {
        return "user/register";
    }

    /**
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/login")
    public String login() {
        return "user/login";
    }

    /**
     * Endpoint for getting a summary.
     *
     * @param input The input from the user.
     * @param model The model to use.
     * @return The name of the view to render.
     * @throws IOException If an I/O error occurs.
     */
    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "input") String input,
            Model model
    ) throws IOException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String username = "You";
        String output;
        String url;

        input = input.trim();
        boolean isURL = isValidURL(input);

        //if (auth instanceof AnonymousAuthenticationToken) {
        // set username to logged in name
        // change login button text to account / logout
        //}

        if (isURL) {
            url = HTMLParser.parser(input);
        }
        else {
            // Facebook needs a valid URL for the share function to work.
            // Without a valid URL, the facebook button will open a window giving the user an error.
            // So, this else statement will set a temp url if the user instead inputs a block of text to parse.
            // If you come up with a better solution or if this is unnecessary, please feel free to edit/remove
            url = "https://www.google.com/";
        }
        try {
            output = bart.queryModel(input);
        } catch (Exception e) {
            output = "Error Occurred. Please try again.";
            //System.out.println("catched");
        }

        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output);

        // Share Button Attributes
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl="+url);
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl="+url);
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl="+url);

        return "api/summary";
    }

    /**
     * Endpoint for creating a user.
     *
     * @param user    The user to create.
     */
    @PostMapping("/user/create")
    public String createUser(
            @RequestParam(value = "email") String email,
            @ModelAttribute User user,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        logger.info("Received user creation request: " + user);
        boolean isRegistered = false;
        try {
            isRegistered = userService.createUser(user) != null;
        } catch (Exception e) {
            logger.warning("User creation failed: " + e.getMessage());
        }
        if (isRegistered) {
            logger.info("User created successfully: " + user);
            //redirectAttributes.addFlashAttribute("success", "User '" + email + "' created successfully.");
            model.addAttribute("isRegistered", true);
            model.addAttribute("message", "<span class=\"bi bi-check-circle-fill\"></span> User '" + email + "' created successfully. Please login.");
            return "user/login";
        }
        //redirectAttributes.addFlashAttribute("error", "Registration for '" + email + "' failed.");
        model.addAttribute("isRegistered", false);
        model.addAttribute("message", "<span class=\"bi bi-exclamation-triangle-fill\"></span> Registration error for user '" + email + "'. Please try again.");
        return "user/register";
    }


    /**
     * Checks if a string is a valid URL.
     *
     * @param urlStr The string to check.
     * @return true if the string is a valid URL, false otherwise.
     */
    private static boolean isValidURL(String urlStr) {
        try {
            // Attempt to create a URL object
            new URL(urlStr).toURI();
            return true;
        } catch (Exception e) {
            // If an exception occurs, URL is not valid
            return false;
        }
    }


//    @GetMapping("/")
//    String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        // If the user is actively logged in, automatically redirect to pro features site
//        // https://stackoverflow.com/questions/13131122/spring-security-redirect-if-already-logged-in
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            model.addAttribute("loginText", "Logout"); // data to send to html page
//            model.addAttribute("loginURL", "/logout");
//            return "index";
//        }
//        model.addAttribute("loginText", "Login"); // data to send to html page
//        model.addAttribute("loginURL", "/login");
//        return "index"; // webpage name
//    }
}