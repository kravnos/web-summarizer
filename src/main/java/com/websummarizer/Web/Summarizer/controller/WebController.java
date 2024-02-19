package com.websummarizer.Web.Summarizer.controller;

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

        String username = "You";
        //if (bLoggedIn == true) {
            // set username to logged in name
        //}
        //String input = "Input text or URL";
        String output = "<< Example Data from WebController >> The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog<br /><br />The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog The quick brown fox jumped over the lazy dog << END >>";

        model.addAttribute("date", dateFormat.format(date));
        model.addAttribute("user", username);
        model.addAttribute("input", input);
        model.addAttribute("output", output);
        return "api/summary";
    }

}
