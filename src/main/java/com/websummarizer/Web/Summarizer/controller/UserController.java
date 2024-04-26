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

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    @Autowired
    private HistoryService historyService; //todo service remove
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/")
    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/add-new-history/")
    public ResponseEntity<?> addNewHistory(String inputText, String output,String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Create a new history for the user
            logger.info("new history request from : " + email);
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
            logger.severe("Failed to create new user history." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create new user history.");
        }
    }

    @PostMapping("/{short_link}/append-history/")
    public ResponseEntity<?> addToPreviousHistory(@PathVariable String short_link, String inputText, String output,String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Create a new history for the user
            logger.info("new history append request from : " + email);
            History history = new History();
            assert user != null;
            history.setUser(user);
            history.setInputText(inputText);
            history.setHistoryContent(output);
            history.setUploadTime(LocalDateTime.now());

            //String shortLink = String.valueOf(historyRepo.findHistoryByShortLink(short_link));

            history.setShort_link(short_link);

            // Save the new history
            History savedHistory = historyService.save(history);

            logger.info("new user history created successfully: " + savedHistory.getId());
            return ResponseEntity.ok(savedHistory);
        } catch (Exception e) {
            logger.severe("failed to create new user history." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to append new user history.");
        }
    }

    @GetMapping("/histories")
    public ResponseEntity<?> addToPreviousHistory(String email) {
        try {
            User user = userRepo.findByEmail(email).orElse(null);
            // Create a new history for the user
            logger.info("fetching histories for : " + email);

            // Save the new history
            assert user != null;
            List<History> allHistories = historyService.findAllHistory(user.getId());

            logger.info("returning all histories for : "+email);
            return ResponseEntity.ok(allHistories);
        } catch (Exception e) {
            logger.severe("failed to return histories." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to return histories.");
        }
    }
}
