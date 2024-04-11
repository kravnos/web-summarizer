package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.BitLyRequest;
import com.websummarizer.Web.Summarizer.services.BitlyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class BitLyController {

    @Autowired
    BitlyService bitLyService;

    @PostMapping("/processBitly")
    public String processBitly(String longURL){

        String shortURL = bitLyService.getShortURL(longURL);

        return shortURL;
    }
}
