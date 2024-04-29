package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

/**
 * Controller class for handling user-related requests.
 */
@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    @Autowired
    private HistoryService historyService; // Service for history operations
    @Autowired
    private UserRepo userRepo; // Repository for user data access
    @Autowired
    private ShortLinkGenerator shortLinkGenerator; // Short link generator service
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    /**
     * Endpoint to get the email of the authenticated user.
     *
     * @return The email of the authenticated user.
     */
    @GetMapping("/")
    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    /**
     * Endpoint to add a new history record for a user.
     *
     * @param inputText    The input text.
     * @param output       The output text.
     * @param email        The user's email.
     * @return ResponseEntity containing the saved history if successful, otherwise an error response.
     */
    @PostMapping("/add-new-history")
    public ResponseEntity<?> addNewHistory(String inputText, String output, String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Create a new history for the user
            logger.info("New history request from: " + email);
            History history = new History();
            assert user != null;
            history.setUser(user);
            history.setInputText(inputText);
            history.setHistoryContent(output);
            history.setUploadTime(LocalDateTime.now());
            history.setShort_link(shortLinkGenerator.generateShortUrl());

            // Save the new history
            History savedHistory = historyService.save(history);

            logger.info("New user history created successfully: " + savedHistory.getId());
            return ResponseEntity.ok(savedHistory);
        } catch (Exception e) {
            logger.severe("Failed to create new user history: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create new user history.");
        }
    }

    /**
     * Endpoint to append to a previous history record for a user.
     *
     * @param short_link   The short link identifier of the previous history.
     * @param inputText    The input text to append.
     * @param output       The output text to append.
     * @param email        The user's email.
     * @return ResponseEntity containing the saved history if successful, otherwise an error response.
     */
    @PostMapping("/{short_link}/append-history")
    public ResponseEntity<?> addToPreviousHistory(@PathVariable String short_link, String inputText, String output, String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Create a new history for the user
            logger.info("New history append request from: " + email);
            History history = new History();
            assert user != null;
            history.setUser(user);
            history.setInputText(inputText);
            history.setHistoryContent(output);
            history.setUploadTime(LocalDateTime.now());
            history.setShort_link(short_link);

            // Save the new history
            History savedHistory = historyService.save(history);

            logger.info("New user history created successfully: " + savedHistory.getId());
            return ResponseEntity.ok(savedHistory);
        } catch (Exception e) {
            logger.severe("Failed to append new user history: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to append new user history.");
        }
    }

    /**
     * Endpoint to get all histories for a user.
     *
     * @param email The user's email.
     * @return ResponseEntity containing the user's histories if successful, otherwise an error response.
     */
    @GetMapping("/histories")
    public ResponseEntity<?> getHistories(String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Fetch histories for the user
            logger.info("Fetching histories for: " + email);

            // Return all histories
            assert user != null;
            List<History> allHistories = historyService.findAllHistory(user.getId());

            logger.info("Returning all histories for: " + email);
            return ResponseEntity.ok(allHistories);
        } catch (Exception e) {
            logger.severe("Failed to return histories: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to return histories.");
        }
    }
}
