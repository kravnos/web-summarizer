package com.websummarizer.Web.Summarizer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * Controller class for generating short links.
 */
@RestController
public class ShortLinkGenerator {

    @Value("${WEBADDRESS}")
    private String webAddress;

    /**
     * Generates a short URL based on a random code.
     *
     * @return Short URL.
     */
    public String generateShortUrl(){
        String url;
        Random rand = new Random();
        url = webAddress + rand.hashCode(); // add random to avoid duplicate code
        int num = Math.abs(url.hashCode());
        String dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder out = new StringBuilder();
        int len = dic.length();
        while (num > 0){
            out.insert(0, dic.charAt(num % len));
            num = num / len;
        }
        return out.toString();
    }
}
