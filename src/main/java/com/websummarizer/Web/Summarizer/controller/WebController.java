package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
     * Endpoint for getting a summary.
     *
     * @param input The input from the user.
     * @param model The model to use.
     * @return The name of the view to render.
     * @throws IOException If an I/O error occurs.
     */
    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "first_name", required = false) String username,
            @RequestParam(value = "input") String input,
            Model model
    ) throws IOException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String output;
        String url;

        input = input.trim();
        boolean isURL = isValidURL(input);

        if ((username == null) || (username.equals("undefined"))) {
            username = "You";
        }

        if (isURL) {
            url = HTMLParser.parser(input);
        } else {
            /* TODO: urls are not shown in the social links */
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
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl=" + url);
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl=" + url);
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl=" + url);

        return "api/summary";
    }

    /**
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/login")
    public String login(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "path", required = false) String path,
            Model model
    ) {
        if ((isLoggedIn != null) && (isLoggedIn.equals("true"))) {
            if ((path != null) && (path.equals("pro"))) {
                if ((isProUser != null) && (isProUser.equals("true"))) {
                    model.addAttribute("isProUser", true);

                    return "user/thankyou";
                } else {
                    return "user/pro";
                }
            } else {
                model.addAttribute("isLoggedIn", true);

                return "user/account";
            }
        } else {
            return "user/login";
        }
    }

    @PostMapping("/user/pro")
    public String pro(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            Model model
    ) {
        if ((isLoggedIn != null) && (isLoggedIn.equals("true"))) {
            if ((isProUser != null) && (isProUser.equals("true"))) {
                model.addAttribute("isProUser", true);

                return "user/thankyou";
            } else {
                return "user/pro";
            }
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Please login to unlock or purchase pro features.");

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
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            Model model
    ) {
        boolean isValidPurchase = true; // Not real payment, therefore always accept

        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isValidPurchase) {
            model.addAttribute("isProUser", true);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Payment successful. Thank you for your purchase.");

            return "user/thankyou";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Payment processing failed. Please try again.");

            return "user/pro";
        }
    }

    /**
     * Endpoint for cancelling membership.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/cancel")
    public String cancel(
            Model model
    ) {
        model.addAttribute("isValid", true);
        model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
        model.addAttribute("message", "Membership has been cancelled. You will no longer be billed.");

        return "user/cancel";
    }

    /**
     * Endpoint for user registration.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/register")
    public String register(
            @RequestParam(value = "login_email") String email,
            Model model
    ) {
        model.addAttribute("email", email);

        return "user/register";
    }

    /**
     * Endpoint for user account settings.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/account")
    public String account(
            @RequestParam(value = "account_email", required = false) String email,
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            Model model
    ) {
        boolean isValidUpdate = true;      /* TODO: push user changes to the DB */

        model.addAttribute("email", email);
        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isValidUpdate) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Account settings for '" + email + "' successfully updated.");
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Failed to save settings for '" + email + "'. Please try again.");
        }

        return "user/account";
    }

    /**
     * Endpoint for creating a user.
     *
     * @param user The user to create.
     */
    @PostMapping("/user/create")
    public String createUser(
            @RequestParam(value = "first_name") String first_name,
            @RequestParam(value = "email") String email,
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

        if (isRegistered) {
            model.addAttribute("first_name", first_name);
            model.addAttribute("email", email);
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
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "path", required = false) String path,
            Model model
    ) {
        boolean isValidLogin = true;        /* TODO: validate login credentials against the DB */

        if (isValidLogin) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' logged in successfully.");

            if ((path != null) && (path.equals("pro"))) {
                if ((isProUser != null) && (isProUser.equals("true"))) {
                    model.addAttribute("isProUser", true);

                    return "user/thankyou";
                } else {
                    return "user/pro";
                }
            } else {
                model.addAttribute("email", email);

                return "user/account";
            }
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Login auth error for '" + email + "'. Please try again.");

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
}