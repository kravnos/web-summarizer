package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.model.User;

public class UserMapper {

    // Map UserReqAto to User entity
    public static User mapUserReqAtoToEto(UserReqAto reqAto) {
        return User.builder()
                .first_name(reqAto.getFirst_name())
                .last_name(reqAto.getLast_name())
                .email(reqAto.getEmail())
                .phone_number(reqAto.getPhone_number())
                .password(reqAto.getPassword())
                .build();
    }

    // Map User entity to UserResAto
    public static UserResAto mapUserEtoToResAto(User user) {
        return UserResAto.builder()
                .id(user.getId())
                .first_name(user.getFirst_name())
                .last_name(user.getLast_name())
                .email(user.getEmail())
                .phone_number(user.getPhone_number())
                .password(user.getPassword())
                .build();
    }

    // Update fields of an existing User entity
    public static void updateUser(User user, UserReqAto update) {
        user.setFirst_name(update.getFirst_name());
        user.setLast_name(update.getLast_name());
        user.setEmail(update.getEmail());
        user.setPhone_number(update.getPhone_number());
        user.setPassword(update.getPassword());
    }
}
