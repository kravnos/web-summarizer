package com.COSC4P02.BrockWebSummarizer;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

import java.util.Map;

@Controller
public class WebController {

    @GetMapping("/")
    String index(Model model) {
        model.addAttribute("index", "Data from WebController"); // data to send to html page
        return "index"; // webpage name
    }

    @GetMapping("/pro")
    String pro(Model model, @AuthenticationPrincipal OAuth2User principal) {
        System.out.println(principal.getAttributes());

        // Return pro site with additional features
        model.addAttribute("pro", "Data from WebController");
        return "pro";
    }
}
