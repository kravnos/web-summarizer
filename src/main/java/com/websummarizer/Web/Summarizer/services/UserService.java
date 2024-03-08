package com.websummarizer.Web.Summarizer.services;


import com.websummarizer.Web.Summarizer.model.User;

public interface UserService {
    User createUser(User user);

    // These methods are used for: password and request token
    void setPassword(User user);
    User getUserByPasswordResetToken(String token);
    int setPasswordRequestToken(String token, User user);
}
