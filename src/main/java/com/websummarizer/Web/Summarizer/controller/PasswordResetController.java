package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.common.exceptions.PasswordResetHTTPStatus;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.logging.Logger;

@Controller
public class PasswordResetController {

    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JavaMailSender emailSender;
    private static final Logger logger = Logger.getLogger("PasswordResetController");

    /**
     * Endpoint for requesting the modal where the user's inputs their email of the account with the password to reset.
     *
     * @param email The email the user submitted.
     * @param model Self-explanatory
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
     * @param email The email the user submitted.
     * @param model Self-explanatory
     * @param response HTTP Response code to edit (for user testing)
     * @return The name of the view to render.
     */
    @PostMapping("/user/send")
    @Transactional
    public String send(
            @RequestParam(value = "code_email") String email,
            Model model,
            HttpServletResponse response
    ) {
        User temp = userService.getUserByEmail(email);
        model.addAttribute("email", email);

        if (temp != null) {
            model.addAttribute("isValid", true);
            model.addAttribute("html", "<span class=\"bi bi-check-circle-fill\"></span>");
            model.addAttribute("message", "Authentication Code sent to '" + email + "'. Please check your inbox.");

            //  Set reset token
            String token = RandomStringUtils.randomAlphanumeric(6);    // Authentication code/Reset Token
            userService.setPasswordRequestToken(token, temp);

            //  Create email body
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Password Reset");
            message.setText("This is a test for a school project. Please delete if you got this by accident.");
            message.setText("Reset password authentication code: \n\n" + token);

            //  Send email
            try{
                emailSender.send(message);
                logger.info("Email successfully sent to: " + email);
            } catch (MailParseException m){
                logger.warning("There was an error sending the email");
                logger.warning("Error Message: " + m.getMessage());
                logger.warning("Error Cause: " + m.getCause());
                response.setStatus(PasswordResetHTTPStatus.EMAIL_PARSE_ERROR());
            }

            return "user/reset";
        } else {
            model.addAttribute("isValid", false);
            model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
            model.addAttribute("message", "No account found for '" + email + "'. Please try again.");
            response.setStatus(PasswordResetHTTPStatus.EMAIL_NOT_FOUND());

            return "user/code";
        }
    }

    /**
     * Endpoint for reset password.
     *
     * @param email The email the user submitted.
     * @param password The new password
     * @param code The code used to identify that the user made the reset request
     * @param model Self-explanatory
     * @param response HTTP Response code to edit (for user testing)
     * @return The name of the view to render.
     */
    @PostMapping("/user/reset")
    @Transactional
    public String reset(
            @RequestParam(value = "reset_email") String email,
            @RequestParam(value = "reset_password") String password,
            @RequestParam(value = "reset_code") String code,
            Model model,
            HttpServletResponse response
    ) {
        User temp = userService.getUserByEmailAndResetToken(email, code);

        if (temp != null) {
            temp.setPassword(password);
            userService.setPassword(temp);
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
            response.setStatus(PasswordResetHTTPStatus.INVALID_TOKEN());

            return "user/reset";
        }
    }
}
