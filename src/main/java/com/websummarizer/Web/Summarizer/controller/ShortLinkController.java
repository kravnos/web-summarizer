package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.HistoryResAto;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShortLinkController {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserServiceImpl userService;

    // Get mapping to retrieve a short link record based on shortlink
    @GetMapping("/{shortlink}")
    public String getShortLink(@PathVariable String shortlink, Model model) {
        List<HistoryResAto> histories = historyService.getShortLink(shortlink);
        model.addAttribute("histories", histories);
        return "shortContent/index";
    }
}
