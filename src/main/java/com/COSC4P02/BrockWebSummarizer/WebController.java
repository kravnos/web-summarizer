package com.COSC4P02.BrockWebSummarizer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class WebController {

    @GetMapping
    String test(Model model) {
        model.addAttribute("test", "Test from controller in Java"); // data to send to html page
        return "index"; // webpage name
    }

}
