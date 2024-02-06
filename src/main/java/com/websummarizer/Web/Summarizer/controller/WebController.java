package com.websummarizer.Web.Summarizer.controller;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/api/summary")
    String getSummary(Model model) {
        model.addAttribute("summaryText", "<< Data from WebController >>"); // data to send to html page
        return "api/summary"; // webpage to return
    }

    @GetMapping("/")
    String index(Model model, @AuthenticationPrincipal OAuth2User principal) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // If the user is actively logged in, automatically redirect to pro features site
        // https://stackoverflow.com/questions/13131122/spring-security-redirect-if-already-logged-in
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            //System.out.println(principal.getAttributes());
            System.out.println(principal.getName());
            System.out.println((String) principal.getAttribute("name"));
            System.out.println((String) principal.getAttribute("email"));
            model.addAttribute("loginText", "Logout"); // data to send to html page
            model.addAttribute("loginURL", "/logout");
            return "index";
        }
        model.addAttribute("loginText", "Login"); // data to send to html page
        model.addAttribute("loginURL", "/login");
        return "index"; // webpage name
    }
}
