package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.bart.Bart;
import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
public class WebController {

    @Autowired
    private final Bart bart;

    public WebController(Bart bart) {
        this.bart = bart;
    }

    @GetMapping("/register")
    public String register() {
        return "index";
    }

    @GetMapping("/signin")
    public String signIn(){
        return "index";
    }

    @PostMapping("/api/summary")
    public String getSummary(
            @RequestParam(value = "input") String input,
            Model model
    ) throws IOException {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd h:mm:ss a");

        String username = "You";
        String output;

        boolean isURL = isValidURL(input);

        if(isURL) {
            input = HTMLParser.parser(input);
        }
        try {
            output = bart.queryModel(input);
        }catch (Exception e){
            output = "Error Occured";
            System.out.println("catched");
        }


        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output);
        return "api/summary";
    }

    private static boolean isValidURL(String urlStr) {
        try {
            // Attempt to create a URL object
            new URL(urlStr).toURI();
            return true;
        } catch (Exception e) {
            // If an exception occurs, URL is not valid
            return false;
        }
    }


//    @GetMapping("/")
//    String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//        // If the user is actively logged in, automatically redirect to pro features site
//        // https://stackoverflow.com/questions/13131122/spring-security-redirect-if-already-logged-in
//        if (!(auth instanceof AnonymousAuthenticationToken)) {
//            model.addAttribute("loginText", "Logout"); // data to send to html page
//            model.addAttribute("loginURL", "/logout");
//            return "index";
//        }
//        model.addAttribute("loginText", "Login"); // data to send to html page
//        model.addAttribute("loginURL", "/login");
//        return "index"; // webpage name
//    }
}