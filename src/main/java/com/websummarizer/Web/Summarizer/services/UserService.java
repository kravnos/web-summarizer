package com.websummarizer.Web.Summarizer.services;


import com.websummarizer.Web.Summarizer.model.User;

public interface UserService {
    User createUser(User user);

    // These methods are used for: password and request token
    void setPassword(User user);
    User getUserByEmail(String email);
    User getUserByEmailAndResetToken(String email, String token);
    int setPasswordRequestToken(String token, User user);
}
