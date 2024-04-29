package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

/**
 * Controller class for handling password reset functionality.
 */
@Controller
public class PasswordResetController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private AuthenticationController authenticationController;
    @Autowired
    private JavaMailSender emailSender;
    private static final Logger logger = Logger.getLogger("PasswordResetController");

    /**
     * Endpoint for requesting the modal where the user inputs their email for password reset.
     *
     * @param email The email submitted by the user.
     * @param model Model object for rendering the view.
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
     * Endpoint for sending authentication code to the email submitted.
     *
     * @param email The email submitted by the user.
     * @param model Model object for rendering the view.
     * @return The name of the view to render.
     */
    @PostMapping("/user/send")
    @Transactional
    public String send(
            @RequestParam(value = "code_email") String email,
            Model model
    ) {
        User temp;
        try{
            temp = (User) userService.loadUserByUsername(email);
        } catch (UsernameNotFoundException u){
            temp = null;    // Return null if no user was found
        }

        if (temp != null) {
            //  Set reset token
            String token = RandomStringUtils.randomAlphanumeric(6);    // Authentication code/Reset Token
            userService.setPasswordRequestToken(token, temp);

            //  Create email body
            try{
                SimpleMailMessage message = new SimpleMailMessage();
                message.setTo(email);
                message.setSubject("Password Reset");
                message.setText("This is a test for a school project. Please delete if you got this by accident.");
                message.setText("Reset password authentication code: \n\n" + token);

                //  Send email
                try {
                    emailSender.send(message);
                    model.addAttribute("email", email);
                    model.addAttribute("isValid", true);
                    model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
                    model.addAttribute("message", "Authentication Code sent to '" + email + "'. Please check your inbox.");
                    logger.info("Email successfully sent to: " + email);
                    return "user/reset";
                }
                //  Run if there was a problem with the parsing of the email
                catch (MailParseException m) {
                    model.addAttribute("isValid", false);
                    model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
                    model.addAttribute("message", "Problem detected with the server's email functionality. Please contact an administrator.");
                    logger.severe("There was an error sending the email");
                    logger.severe("Error Message: " + m.getMessage());
                    logger.severe("Error Cause: " + m.getCause());
                    return "user/code";
                }
            }

            //  Runs if there is a problem with the SMTP config properties
            catch (Exception e){
                model.addAttribute("isValid", false);
                model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
                model.addAttribute("message", "Problem detected with the server's email functionality. Please contact an administrator.");
                logger.severe("Problem with SMTP configuration. (Possibly invalid username or app password in env.properties?)");
                logger.severe("Error Message: " + e.getMessage());
                logger.severe("Error Cause: " + e.getCause());
                return "user/code";
            }

        }
        // If null, then return invalid email message to user
        model.addAttribute("isValid", false);
        model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
        model.addAttribute("message", "No account found for '" + email + "'. Please try again.");

        return "user/code";
    }

    /**
     * Endpoint for resetting password.
     *
     * @param email    The email submitted by the user.
     * @param password The new password.
     * @param code     The code used to identify the user's reset request.
     * @param model    Model object for rendering the view.
     * @return The name of the view to render.
     */
    @PostMapping("/user/reset")
    @Transactional
    public String reset(
            @RequestParam(value = "reset_email") String email,
            @RequestParam(value = "reset_password") String password,
            @RequestParam(value = "reset_code") String code,
            Model model
    ) {
        User temp = userService.getUserByEmailAndResetToken(email, code);
        if (temp != null) {
            temp.setPassword(password);
            authenticationController.registerUser(temp);
            userService.setPasswordRequestToken(null, temp);

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

}
