package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.bart.OpenAi;
import com.websummarizer.Web.Summarizer.controller.shortlink.Shortlink;
import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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
    private final OpenAi openAi;

    @Autowired
    private AuthenticationController authenticationController;

    @Autowired
    Shortlink shortlink;

    @Autowired
    BitLyController bitLyController;

    @Value("${WEBADDRESS}")
    private String webAddress;

    private static final Logger logger = Logger.getLogger(WebController.class.getName());

    /**
     * Constructor for WebController.
     *
     * @param bart The Bart instance to use.
     */
    public WebController(Bart bart, OpenAi openAi) {
        this.bart = bart;
        this.openAi = openAi;
    }

    /**
     * Endpoint for getting a summary.
     *
     * @param input The input from the user.
     * @param model The model to use.
     * @return The name of the view to render.
     */
    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "first_name", required = false) String username,
            @RequestParam(value = "input") String input,
            Model model, HttpSession session
    ) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String output = null;   // This stores the summarized web content
        String url = null;      // This stores the shortened URL***

        input = input.trim();
        boolean isURL = isValidURL(input);

        if ((username == null) || (username.equals("undefined"))) {
            username = "You";
        }

        if (isURL) {
            logger.info("got the URL:" + input);
            try {
                //***todo: add short links instead of using the actual URL
                url = input;
                output = bart.queryModel(HTMLParser.parser(input));
            } catch (IOException e) {
                output = "Error Occurred. Please try again.";
            }
        } else {
            try {
                logger.info("got the text:" + input);
                output = bart.queryModel(input);
                url = webAddress;
            } catch (Exception e) {
                output = "Error Occurred while fetching your results. Please try again.";
            }
        }

        String shortlinkCode = shortlink.Shortlink(input, output, session);


        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output + " : "+ shortlinkCode);

        // Share Button Attributes
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl=" + url); //todo: add short links to share
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl=" + url); //todo: add short links to share
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl=" + url); //todo: add short links to share

        return "api/summary";
    }

    /**
     * Endpoint for getting a summary.
     *
     * @param input The input from the user.
     * @param model The model to use.
     * @return The name of the view to render.
     */
    @PostMapping("/api/summary/openai")
    public String getSummaryOpenai(
            @RequestParam(value = "first_name", required = false) String username,
            @RequestParam(value = "input") String input,
            Model model, HttpSession session
    ) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String output = null;   // This stores the summarized web content
        String url = null;      // This stores the shortened URL***

        input = input.trim();
        boolean isURL = isValidURL(input);

        if ((username == null) || (username.equals("undefined"))) {
            username = "You";
        }

        if (isURL) {
            logger.info("got the URL:" + input);
            try {
                //***todo: add short links instead of using the actual URL
                url = input;
                output = openAi.queryModel(HTMLParser.parser(input));
            } catch (IOException e) {
                output = "Error Occurred. Please try again.";
            }
        } else {
            try {
                logger.info("got the text:" + input);
                output = openAi.queryModel(input);
                url = webAddress;
            } catch (Exception e) {
                output = "Error Occurred while fetching your results. Please try again.";
            }
        }

        String shortlinkCode = shortlink.Shortlink(input, output, session);


        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output + " : "+ shortlinkCode);

        // Share Button Attributes
        model.addAttribute("fb", "https://www.addtoany.com/add_to/facebook?linkurl=" + url); //todo: add short links to share
        model.addAttribute("twitter", "https://www.addtoany.com/add_to/x?linkurl=" + url); //todo: add short links to share
        model.addAttribute("email", "https://www.addtoany.com/add_to/email?linkurl=" + url); //todo: add short links to share

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
                model.addAttribute("isProUser", isProUser);
                /*
                    TODO:
                      Get all user details from db and add to model
                      Require: first name, last, phone, email, do not add password.
                */

                return "user/account";
            }
        } else {
            return "user/login";
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

            // Retrieve the session associated with the user's request or create a new one if it doesn't exist
            HttpSession session = request.getSession();
            // Store the user's login email in the session under the attribute name "username"
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
     * Endpoint for user account settings.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/user/account")
    public String account(
            @RequestParam(value = "account_email", required = false) String email,
            @RequestParam(value = "isLoggedIn") String isLoggedIn,
            @RequestParam(value = "isProUser", required = false) String isProUser,
            @ModelAttribute UserReqAto user,
            Model model
    ) {
        logger.info("User update request for the following user: "+user);
        ResponseEntity<?> isValidUpdate = authenticationController.updateUser(user);      /* TODO: push user changes to the DB */

        model.addAttribute("email", email);
        model.addAttribute("isLoggedIn", isLoggedIn);
        model.addAttribute("isProUser", isProUser);

        if (isValidUpdate != null) {
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

        //check password
        if(!shortlink.checkPassword(user.getPassword())) {
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