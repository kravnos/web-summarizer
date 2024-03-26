package com.websummarizer.Web.Summarizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) class for user login.
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
    /**
     * Email address for user login.
     */
    private String login_email;

    /**
     * Password for user login.
     */
    private String login_password;
}