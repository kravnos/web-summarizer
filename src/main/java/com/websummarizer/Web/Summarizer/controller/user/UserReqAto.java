package com.websummarizer.Web.Summarizer.controller.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Builder
@Getter
@ToString(onlyExplicitlyIncluded = true)
/**
 * model representing API request model to add / register a user
 */
public class UserReqAto {

    @NonNull
    @ToString.Include
    private String first_name;

    @NonNull
    @ToString.Include
    private String last_name;

    @NonNull
    @ToString.Include
    String email;

    @NonNull
    @ToString.Include
    String password;

    @NonNull
    @ToString.Include
    String phone_number;

    @NonNull
    @ToString.Include
    String request_token;
}
