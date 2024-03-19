package com.websummarizer.Web.Summarizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class RegistrationDTO {
    private String login_email;
    private String login_password;
}
