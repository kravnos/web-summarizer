package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.common.exceptions.PasswordResetHTTPStatus;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;
import java.util.logging.Logger;

@Controller
public class PasswordResetController {

    @Value("${WEBADDRESS}")
    private String webaddress;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private JavaMailSender emailSender;
    private static final Logger logger = Logger.getLogger("PasswordResetController");

    /**
     * Runs when a user selects "Forgot Password" and submits an email address
     *
     * @param user The string to check.
     * @param response HTTP Response code
     * @return redirect user back to the main page of the website
     */
    @PostMapping("emailResetPW")
    @Transactional
    public String resetPW(@ModelAttribute User user, HttpServletResponse response){
        /*  Check if the email address is valid
            Note: The email input box on the website strictly follows the email address convention.
            As a result, it would be fairly difficult for the user to input an invalid email address format.
            But, just in case, this is here as a backup.
        */
        boolean isEmailValid = EmailValidator.getInstance().isValid(user.getEmail());
        if (!isEmailValid){
            logger.warning("Invalid Email: " + user.getEmail());
            response.setStatus(PasswordResetHTTPStatus.INVALID_EMAIL());
            return "index";
        }

        //  Build unique password reset URL
        String token = UUID.randomUUID().toString();
        String resetURL = webaddress + "password-reset?token=" + token;
        int usersUpdated = userService.setPasswordRequestToken(token, user);  // Should be either 0 or 1
        if (usersUpdated <= 0 ^ usersUpdated > 1) {
            logger.warning("There is no user in the database with this email: " + user.getEmail());
            response.setStatus(PasswordResetHTTPStatus.EMAIL_NOT_FOUND());
            return "index";
        }
        logger.info("User with email: " + user.getEmail() + " has the following reset token saved: " + token);

        //  Create email body
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Password Reset");
        message.setText("This is a test for a school project. Please delete if you got this by accident.");
        message.setText("Reset password link: " + resetURL);

        //  Send email
        try{
            emailSender.send(message);
            logger.info("Email successfully sent to: " + user.getEmail());
            response.setStatus(PasswordResetHTTPStatus.OK());
        } catch (MailParseException m){
            logger.warning("There was an error sending the email");
            logger.warning("Error Message: " + m.getMessage());
            logger.warning("Error Cause: " + m.getCause());
            response.setStatus(PasswordResetHTTPStatus.EMAIL_PARSE_ERROR());
        }
        return "index";
    }

    /**
     * Ensure that the right user has actually made the request by checking the request token
     *
     * @param token The string used to identify the user that made the password reset request
     * @param session Session to contain the valid user data
     * @param response HTTP Response code
     * @return redirect to main page if user with a specified token isn't found; redirect to password reset page otherwise
     */
    @GetMapping("password-reset")
    public String checkRequestToken(@RequestParam String token, HttpSession session, HttpServletResponse response){
        User userPWToChange = userService.getUserByPasswordResetToken(token);
        if (userPWToChange == null) {
            logger.warning("There is no user with the specified reset token in the database");
            response.setStatus(PasswordResetHTTPStatus.INVALID_TOKEN());
            return "index";
        }
        logger.info("User successfully pulled: " + userPWToChange);
        session.setAttribute("user", userPWToChange);
        response.setStatus(PasswordResetHTTPStatus.SUCCESS_REDIRECT_TO_RESET_FORM());
        return "password-reset";
    }

    /**
     * Successfully updates the specified user's password (and sets the request token column to null)
     *
     * @param newPW The password from the form data (Only value is newPW.getPassword())
     * @param session Session containing the retrieved user whose password is being updated
     * @return redirect to main page
     */
    @PostMapping("saveNewPW")
    @Transactional
    public String saveNewPW(@ModelAttribute User newPW, HttpSession session){
        User user = (User) session.getAttribute("user");
        user.setPassword(newPW.getPassword());
        userService.setPassword(user);
        userService.setPasswordRequestToken(null, user);
        return "redirect:/";
    }
}
