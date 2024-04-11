package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.shortlink.Shortlink;
import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Controller for web-related actions.
 */
@Controller
public class WebController {

    @Autowired
    private final Bart bart;
    @Autowired
    private AuthenticationController authenticationController;
    private static final Logger logger = Logger.getLogger(Bart.class.getName());
    @Autowired
    Shortlink shortlink;

    @Autowired
    BitLyController bitLyController;

    @Autowired
    HistoryController historyController;

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
            Model model, HttpSession session
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

        String ShortlinkCode = shortlink.Shortlink(input, output, session);



        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output + " : " + ShortlinkCode);

        // Share Button Attributes
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl=" + url);
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl=" + url);
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl=" + url);

        return "api/summary";
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
        String password = user.getPassword();


        //check password
        if(!shortlink.checkPassword(password)) {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character");

            return "user/register";
        }


        try {
            ResponseEntity<?> registerResponse = authenticationController.registerUser(user);
            isRegistered = registerResponse.getStatusCode().is2xxSuccessful();
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
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/login")
    public String login(
            @RequestParam(value = "isLoggedIn", required = false) String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @RequestParam(value = "path", required = false) String path,
            @ModelAttribute UserDTO userDTO,
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
     * Endpoint for send authentication code.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/code")
    public String code(
            @RequestParam(value = "login_email") String email,
            Model model
    ) {
        model.addAttribute("email", email);

        return "user/code";
    }

    /**
     * Endpoint for send authentication code.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/send")
    public String send(
            @RequestParam(value = "code_email") String email,
            Model model
    ) {
        boolean isValidEmail = true;        /* TODO: validate email exists in the DB */

        model.addAttribute("email", email);

        if (isValidEmail) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Authentication Code sent to '" + email + "'. Please check your inbox.");

            return "user/reset";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "No account found for '" + email + "'. Please try again.");

            return "user/code";
        }
    }

    /**
     * Endpoint for reset password.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/reset")
    public String reset(
            @RequestParam(value = "reset_email") String email,
            @RequestParam(value = "reset_password") String password,
            @RequestParam(value = "reset_code") String code,
            Model model
    ) {
        boolean isValidEmail = true;    /* TODO: validate email exists in the DB */
        boolean isValidCode = true;     /* TODO: validate auth with code sent in email */

        if ((isValidEmail) && (isValidCode)) {
            model.addAttribute("email", email);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Password for account '" + email + "' has been reset. Please login.");

            return "user/login";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Authentication error for '" + email + "'. Please try again.");

            return "user/reset";
        }
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
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            Model model
    ) {
        boolean isValidUpdate = false;      /* TODO: validate account changes in the DB */

        model.addAttribute("isLoggedIn", isLoggedIn);

        if (isValidUpdate) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Account settings for 'email' successfully updated.");
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "Failed to save settings for 'email'. Please try again.");
        }

        return "user/account";
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
            @ModelAttribute UserDTO userDTO,
            Model model, HttpServletRequest request
    ) {
        ResponseEntity<?> loginResponse = authenticationController.loginUser(userDTO);

        boolean isValidLogin = loginResponse.getStatusCode().is2xxSuccessful();

        if (isValidLogin) {
            model.addAttribute("isLoggedIn", true);
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "User '" + email + "' logged in successfully.");

            HttpSession session = request.getSession();
            session.setAttribute("username", userDTO.getLogin_email());

            if ((path != null) && (path.equals("pro"))) {
                if ((isProUser != null) && (isProUser.equals("true"))) {
                    model.addAttribute("isProUser", true);

                    return "user/thankyou";
                } else {
                    return "user/pro";
                }
            } else {
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