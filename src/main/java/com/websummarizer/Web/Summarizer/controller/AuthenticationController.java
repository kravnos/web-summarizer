package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.common.exceptions.OauthUpdateNotAllowed;
import com.websummarizer.Web.Summarizer.model.UserReqAto;
import com.websummarizer.Web.Summarizer.model.LoginResponseDTO;
import com.websummarizer.Web.Summarizer.model.UserDTO;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

/**
 * Controller class for handling authentication-related endpoints.
 */
@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {

    private static final Logger logger = Logger.getLogger(AuthenticationController.class.getName());

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Endpoint for user registration.
     *
     * @param user User object containing registration details.
     * @return ResponseEntity containing the registered user or an error message.
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(User user) {
        logger.info("Received user creation request: " + user);
        try {
            User registeredUser = authenticationService.registerUser(user);
            if (registeredUser != null) {
                logger.info("User registered successfully: " + user.getEmail());
                return ResponseEntity.ok(registeredUser);
            } else {
                logger.warning("Failed to register user: " + user.getEmail());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user.");
            }
        } catch (Exception e) {
            logger.severe("Failed to register user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to register user.");
        }
    }

    /**
     * Endpoint for user login.
     *
     * @param userDTO UserDTO object containing login credentials.
     * @return ResponseEntity containing login response DTO or an error message.
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(UserDTO userDTO) {

        logger.info("User login request for: " + userDTO.getLogin_email());
        try {
            LoginResponseDTO loginResponseDTO = authenticationService.loginUser(userDTO.getLogin_email(), userDTO.getLogin_password());
            if (loginResponseDTO.getUser() != null) {
                logger.info("User login successful: " + userDTO.getLogin_email());
                return ResponseEntity.ok(loginResponseDTO);
            } else {
                logger.warning("Invalid credentials for user: " + userDTO.getLogin_email());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
            }
        } catch (Exception e) {
            logger.severe("Failed to login user: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to login user: " + e.getMessage());
        }
    }

    /**
     * Endpoint for updating user information.
     *
     * @param userReqAto UserReqAto object containing updated user information.
     * @return ResponseEntity containing the updated user or an error message.
     */
    @PostMapping("/update-user")
    public ResponseEntity<?> updateUser(UserReqAto userReqAto){
        //todo make sure not to allow user info change for other users
        logger.info("user update request with following info: " + userReqAto);
        try{
            User updatedUser = authenticationService.updateExistingUser(userReqAto);
            if(updatedUser!=null){
                logger.info("user updated successful: "+updatedUser);
                return ResponseEntity.ok(updatedUser);
            }
            else {
                logger.warning("Failed to update, returned user object is null");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
            }
        } catch (OauthUpdateNotAllowed e){
            logger.warning("Failed to update user as user is oauth user: ");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Cannot update information for Google/Github users.");
        }
        catch (Exception e) {
            logger.severe("Failed to update user due to an exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update user.");
        }
    }
}
