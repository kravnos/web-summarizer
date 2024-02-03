package com.websummarizer.Web.Summarizer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @GetMapping("/api/summary")
    String getSummary(Model model) {
        model.addAttribute("summaryText", "<< Data from WebController >>"); // data to send to html page
        return "api/summary"; // webpage to return
    }

}
