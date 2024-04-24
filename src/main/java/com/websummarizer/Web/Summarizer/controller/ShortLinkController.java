package com.websummarizer.Web.Summarizer.controller;


import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import com.websummarizer.Web.Summarizer.services.users.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ShortLinkController {
    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserService userService;

    // Get mapping to retrieve a short link record based on shortlink
    @GetMapping("/{shortlink}")
    public String getShortLink(@PathVariable String shortlink, Model model) {

        var shortLinkResAto = historyService.getShortLink(shortlink);
        var userName = userService.getUserName(shortLinkResAto.getUID());

        model.addAttribute("content", shortLinkResAto);
        model.addAttribute("user", userName);
        return "shortContent/index";
    }

    @GetMapping("/search-Engine")
    public String getSearchEngine(Model model){
        return "shortContent/engine";
    }

    @PostMapping("/content-search")
    public String getContent(@RequestParam("search") String search, Model model, HttpSession session){
        List<HistoryResAto> histories = historyService.findAllHistory();
    }
}
