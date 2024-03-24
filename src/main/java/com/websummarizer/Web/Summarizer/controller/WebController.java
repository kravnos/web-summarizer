package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/login")
    public String login(
            @RequestParam(value = "source") String source,
            Model model
    ) {
        /* TODO:
            check session/boolean/token/method if user is logged in or not
            see user/auth below.
            if logged in then goto user/account endpoint, else goto user/login
         */

        boolean isLoggedIn = false; // update this logic

        if (isLoggedIn) {
            if (source.equals("pro")) {
                return "user/pro";
            } else {
                return "user/account";
            }
        } else {
            model.addAttribute("source", source);

            return "user/login";
        }
    }

    @PostMapping("/user/pro")
    public String pro(
            @RequestParam(value = "source") String source,
            Model model
    ) {
        /* TODO:
            check session/boolean/token/method if user is logged in or not
            if not logged in, then show login form
            if logged in, then show pro purchase form
         */

        boolean isLoggedIn = false; // update this logic

        if (isLoggedIn) {
            return "user/pro";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Please login to unlock or purchase pro features.");
            model.addAttribute("source", source);

            return "user/login";
        }
    }

    /**
     * Endpoint for purchasing pro features.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/purchase")
    public String purchase(
            Model model
    ) {
        boolean isValidPurchase = false;

        if (isValidPurchase) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Payment successful. Thank you for your purchase.");

            return "user/purchase";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Payment processing failed. Please try again.");

            return "user/pro";
        }
    }

    /**
     * Endpoint for user registration.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/register")
    public String register(
            @RequestParam(value = "source") String source,
            Model model
    ) {
        model.addAttribute("source", source);

        return "user/register";
    }

    /**
     * Endpoint for user account settings.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/account")
    public String account(
        Model model
    ) {
        boolean isValidUpdate = false;

        if (isValidUpdate) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Account settings for '<email>' successfully updated.");
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Update error for '<email>'. Please try again.");
        }

        return "user/account";
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

        //if (isLoggedIn) {
        // set username to logged in name
        //}

        if (isURL) {
            url = HTMLParser.parser(input);
        } else {
            /* TODO:
                urls are not shown in the social links
                Please revise
             */
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
            @RequestParam(value = "source") String source,
            @ModelAttribute User user,
            Model model
    ) {
        logger.info("Received user creation request: " + user);
        boolean isRegistered = false;

        try {
            isRegistered = userService.createUser(user) != null;
        } catch (Exception e) {
            logger.warning("User creation failed: " + e.getMessage());
        }

        model.addAttribute("source", source);

        if (isRegistered) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' created successfully. Please login.");

            return "user/login";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Registration error for '" + email + "'. Please try again.");

            return "user/register";
        }
    }

    /**
     * Endpoint for validating the login of a user.
     */
    @PostMapping("/user/auth")
    public String authUser(
            @RequestParam(value = "login_email") String email,
            @RequestParam(value = "login_password") String password,
            @RequestParam(value = "source") String source,
            Model model
    ) {
        /* TODO:
            Please add logic for db check of user credentials for login etc
            Currently accepting all logins to test account settings page.
            Also require a session/boolean/token/method to check if user is now logged in
            to not auth again and bypass login modal
         */

        boolean isValidLogin = true;

        if (isValidLogin) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' logged in successfully.");

            if (source.equals("pro")) {
                return "user/pro";
            } else {
                return "user/account";
            }
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Login auth error for '" + email + "'. Please try again.");
            model.addAttribute("source", source);

            return "user/login";
        }
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