package com.websummarizer.Web.Summarizer.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class WebController {

    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "input") String input,
            Model model
    ) {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");
        //Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        String username = "You";
        //if (auth instanceof AnonymousAuthenticationToken) {
            // set username to logged in name
        //}
        String output = "<< Example Data from WebController >> The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog<br /><br />The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog << END >>";

        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input.trim());
        model.addAttribute("output", output);
        return "api/summary";
    }

    @GetMapping("/")
    String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // If the user is actively logged in, automatically redirect to pro features site
        // https://stackoverflow.com/questions/13131122/spring-security-redirect-if-already-logged-in
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            model.addAttribute("loginText", "Logout"); // data to send to html page
            model.addAttribute("loginURL", "/logout");
            return "index";
        }
        model.addAttribute("loginText", "Login"); // data to send to html page
        model.addAttribute("loginURL", "/login");
        return "index"; // webpage name
    }
}