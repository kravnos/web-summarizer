package com.websummarizer.Web.Summarizer.services.users;

import com.websummarizer.Web.Summarizer.controller.user.UserReqAto;
import com.websummarizer.Web.Summarizer.controller.user.UserResAto;
import com.websummarizer.Web.Summarizer.model.User;

public class UserMapper {

    // Map UserReqAto to User entity
    public static User mapUserReqAtoToEto(UserReqAto reqAto) {
        return User.builder()
                .firstName(reqAto.getFirst_name())
                .LastName(reqAto.getFirst_name())
                .email(reqAto.getEmail())
                .phoneNumber(reqAto.getPhone_number())
                .password(reqAto.getPassword())
                .build();
    }

    // Map User entity to UserResAto
    public static UserResAto mapUserEtoToResAto(User user) {
        return UserResAto.builder()
                .id(user.getId())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .email(user.getEmail())
                .phone_number(user.getPhoneNumber())
                .password(user.getPassword())
                .build();
    }

    // Update fields of an existing User entity
    public static void updateUser(User user, UserReqAto update) {
        user.setFirstName(update.getFirst_name());
        user.setLastName(update.getLast_name());
        user.setEmail(update.getEmail());
        user.setPhoneNumber(update.getPhone_number());
        user.setPassword(update.getPassword());
    }
}
