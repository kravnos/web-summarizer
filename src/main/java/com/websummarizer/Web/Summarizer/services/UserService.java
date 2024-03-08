package com.websummarizer.Web.Summarizer.services;


import com.websummarizer.Web.Summarizer.model.User;

public interface UserService {
    User createUser(User user);

    void resetPassword(User user);
    User getUserByPasswordResetToken(String token);
    int setPasswordResetToken(String token, User user);
}
