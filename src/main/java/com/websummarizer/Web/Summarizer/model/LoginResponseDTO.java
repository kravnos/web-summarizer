package com.websummarizer.Web.Summarizer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data transfer object (DTO) class for login response.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO {
    /**
     * User object associated with the login response.
     */
    private User user;

    /**
     * JWT token associated with the login response.
     */
    private String jwt;
}