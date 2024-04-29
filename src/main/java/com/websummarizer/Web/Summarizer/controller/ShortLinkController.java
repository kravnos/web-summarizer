package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.services.UserServiceImpl;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller class for handling short link requests.
 */
@Controller
public class ShortLinkController {

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserServiceImpl userService;

    /**
     * Retrieves a short link record based on shortlink and returns the associated history.
     *
     * @param shortlink The short link identifier.
     * @param model     Model object for rendering the view.
     * @return The name of the view to render.
     */
    @GetMapping("/{shortlink}")
    public String getShortLink(@PathVariable String shortlink, Model model) {
        List<History> histories = historyService.getShortLink(shortlink);
        String userName = "User";
        if (!histories.isEmpty()) {
            userName = userService.getUserName(histories.get(0).getUser().getId());
        }

        model.addAttribute("shortlink", shortlink);
        model.addAttribute("histories", histories);
        model.addAttribute("user", userName);
        return "index";
    }
}
