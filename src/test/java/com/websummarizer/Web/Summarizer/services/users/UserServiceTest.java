package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.controller.user.UsersResAto;
import com.websummarizer.Web.Summarizer.model.User;
import jakarta.persistence.Table;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.GetMapping;

class UserServiceTest {

    public UserResAto userResAto;
    public UserService userService;
   // public// User name = new User(1234, "test", "test", "test", "test");

    @Test
    UserReqAto addUser() {
      //  UserService.addUser(1234);
        return null;
    }

    @Test
    void getUser() {
        UserService test = this.userService;
        //this.getUser(12345);
        //return getUser();
    }

    @Test
    public UserResAto updateUser() {
       // UserService test = this.userService
        //return this.updateUser(12345, userResAto);
        return null;
        }

    @Test
    void deleteUser() {
        //UserService.deleteUser(12345);
    }

    @Test
    @GetMapping("/Users")
    UsersResAto findAllUser() {
        return userService.findAllUser();
    }


}