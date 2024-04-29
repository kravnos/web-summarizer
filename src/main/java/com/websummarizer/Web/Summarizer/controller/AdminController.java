package com.websummarizer.Web.Summarizer.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for handling admin-related requests.
 */
@RestController
@RequestMapping("/admin")
@CrossOrigin("*") // Allowing cross-origin requests from any domain
public class AdminController {

    /**
     * Handles GET requests to the admin endpoint.
     *
     * @return a String indicating admin level access
     */
    @GetMapping("/")
    public String helloAdmin() {
        return "Admin Level Access";
    }

}
