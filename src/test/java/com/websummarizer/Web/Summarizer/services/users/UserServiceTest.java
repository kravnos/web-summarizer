package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.controller.user.UsersResAto;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;

class UserServiceTest {

    private UserService userService;

    @Test
    void addUser() {

    }

    @Test
    void getUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    @GetMapping("/Users")
    UsersResAto findAllUser() {
        return userService.findAllUser();
    }
}