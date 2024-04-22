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
import java.util.logging.Logger;

@RestController
@RequestMapping("users")
@Slf4j
public class UserController {

    @Autowired
    private HistoryService historyService; //todo service remove
    @Autowired
    private HistoryController historyController;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;
    private static final Logger logger = Logger.getLogger(UserController.class.getName());

    @GetMapping("/")
    public String getAuthenticatedUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @GetMapping("/add-new-history")
    public ResponseEntity<?> addNewHistory(String output) {
        try {
            // Get the currently authenticated user
            String userEmail = this.getAuthenticatedUserEmail();
            logger.info(userEmail);
            User user = userRepo.findByEmail(userEmail).orElse(null);

            if (user == null) {
                logger.warning("Failed to create new user history. User is not authenticated.");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User is not authenticated.");
            }


            // Create a new history for the user
            History history = new History();
            history.setUser(user);
            history.setHistoryContent(output);
            history.setUploadTime(LocalDateTime.now());
            history.setLinkURL(shortLinkGenerator.generateShortUrl());

            // Save the new history
            History savedHistory = historyService.save(history);

            logger.info("New user history created successfully: " + savedHistory.getId());
            return ResponseEntity.ok(savedHistory);
        } catch (Exception e) {
            logger.severe("Failed to create new user history." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create new user history.");
        }
    }

    @GetMapping("{sid}/append-history")
    public ResponseEntity<?> addToPreviousHistory(long hid, String output) {
        try {
            // Get the currently authenticated user
            String userEmail = this.getAuthenticatedUserEmail();
            User user = userRepo.findByEmail(userEmail).orElse(null);

            if (user == null) {
                logger.warning("Failed to create new user history. User is not authenticated.");
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("User is not authenticated.");
            }


            // Create a new history for the user
            History history = new History();
            history.setUser(user);
            history.setHistoryContent(output);
            history.setUploadTime(LocalDateTime.now());



            // Save the new history
            History savedHistory = historyService.save(history);

            logger.info("New user history created successfully: " + savedHistory.getId());
            return ResponseEntity.ok(savedHistory);
        } catch (Exception e) {
            logger.severe("Failed to create new user history." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create new user history.");
        }
    }
}

//    @Autowired
//    private UserService userService;
//
//    // Create a new user
//    @PostMapping
//    public ResponseEntity<UserResAto> createUser(@RequestBody UserReqAto userReqAto) {
//        log.info("Add user request received : " + userReqAto);
//        var userResAto = userService.addUser(userReqAto);
//        log.info("Add user request processed : " + userReqAto);
//        return ResponseEntity.ok(userResAto);
//    }
//
//    // Delete an user by ID
//    @DeleteMapping("/{id}")
//    public ResponseEntity deleteUser(@PathVariable Long id) {
//        log.info("Delete user request received : " + id);
//        userService.deleteUser(id);
//        log.info("Delete user request processed : " + id);
//        return ResponseEntity.ok().build();
//    }
//
//    // Get a user by ID
//    @GetMapping("/{id}")
//    public ResponseEntity<UserResAto> getUser(@PathVariable Long id) {
//        log.info("Get user request received : " + id);
//        var userResAto = userService.getUser(id);
//        log.info("Get user request processed : " + userResAto);
//        return ResponseEntity.ok(userResAto);
//    }
//
//    // Get all users
//    @GetMapping()
//    public ResponseEntity<UsersResAto> findAllUsers() {
//        log.info("Find all user request received");
//        var allUsers = userService.findAllUser();
//        log.info("Find all user request processed. Total count : " + allUsers.getTotalCount());
//        return ResponseEntity.ok(allUsers);
//    }
//
//    // Update a user by ID
//    @PutMapping("/{id}")
//    public ResponseEntity<UserResAto> updateUser(@PathVariable Long id, @RequestBody UserReqAto userReqAto) {
//        log.info(String.format("Update user request received [id=%s, req=%s]", id, userReqAto));
//        var userResAto = userService.updateUser(id, userReqAto);
//        log.info("Update user request processed : " + userResAto);
//        return ResponseEntity.ok(userResAto);
//    }
