package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.controller.shortlink.Shortlink;
import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
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

    @Autowired
    private Shortlink shortlink;

    @Autowired
    private HistoryController historyController;

    @Autowired
    private UserController userController;

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
    @PostMapping("/register")
    public void register() {
        //return "index";
    }

    /**
     * Endpoint for user sign in.
     *
     * @return The name of the view to render.
     */
    @PostMapping("/login")
    public void login() {
        //return "index";
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
        String url = null;

        input = input.trim();
        boolean isURL = isValidURL(input);

        //if (auth instanceof AnonymousAuthenticationToken) {
        // set username to logged in name
        // change login button text to account / logout
        //}

        if (isURL) {
            try {
                url = HTMLParser.parser(input);
            } catch (Exception e) {
                output = "Error Occurred. Please try again.";
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

        // Generate a short code for the given URL
        String ShortlinkCode = shortlink.codeShort(url);
        // Create a new history request object with the generated short code
        HistoryReqAto historyReqAto = new HistoryReqAto(1L, output, ShortlinkCode, LocalDateTime.now());
        // Add the history request to the database and get the response
        historyController.addHistory(historyReqAto);

        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output + " : " + ShortlinkCode);


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
     * @param session The current session.
     * @return The name of the view to render.
     */
    @PostMapping("/createUser")
    public void createUser(@ModelAttribute UserReqAto userReqAto, HttpSession session) {
        session.setAttribute("msg", "");
        logger.info("Received user creation request: " + userReqAto);
        boolean bool = false;
        try {
            userController.createUser(userReqAto);
        } catch (Exception e) {
            session.setAttribute("msg", "Email already exists");
            logger.warning("User creation failed: " + e.getMessage());
        }
        //return "redirect:/";
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
