package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.controller.user.UsersResAto;
import com.websummarizer.Web.Summarizer.services.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/users")
@Slf4j
public final class UserController {

    @Autowired
    private UserService userService;

    // Create a new user
    @PostMapping
    public ResponseEntity<UserResAto> createUser(@RequestBody UserReqAto userReqAto) {
        log.info("Add user request received : " + userReqAto);
        var userResAto = userService.addUser(userReqAto);
        log.info("Add user request processed : " + userReqAto);
        return ResponseEntity.ok(userResAto);
    }

    // Delete an user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id){
        log.info("Delete user request received : " + id);
        userService.deleteUser(id);
        log.info("Delete user request processed : " + id);
        return ResponseEntity.ok().build();
    }

    // Get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserResAto> getUser(@PathVariable Long id) {
        log.info("Get user request received : " + id);
        var userResAto = userService.getUser(id);
        log.info("Get user request processed : " + userResAto);
        return ResponseEntity.ok(userResAto);
    }

    // Get all users
    @GetMapping()
    public ResponseEntity<UsersResAto> findAllUsers() {
        log.info("Find all user request received");
        var allUsers = userService.findAllUser();
        log.info("Find all user request processed. Total count : " + allUsers.getTotalCount());
        return ResponseEntity.ok(allUsers);
    }

    // Update a user by ID
    @PutMapping("/{id}")
    public ResponseEntity<UserResAto> updateUser(@PathVariable Long id, @RequestBody UserReqAto userReqAto) {
        log.info(String.format("Update user request received [id=%s, req=%s]", id, userReqAto));
        var userResAto = userService.updateUser(id, userReqAto);
        log.info("Update user request processed : " + userResAto);
        return ResponseEntity.ok(userResAto);
    }

}
