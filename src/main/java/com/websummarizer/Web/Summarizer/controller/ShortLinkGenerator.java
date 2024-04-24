package com.websummarizer.Web.Summarizer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;


@RestController
public class ShortLinkGenerator {

    @Value("${WEBADDRESS}")
    private String webAddress;

    // Method to generate a short code for a given URL
    public String generateShortUrl(){
        String url;
        Random rand = new Random();
        url = webAddress + rand.hashCode(); //add random avoiding duplicate code
        int num = Math.abs(url.hashCode());
        String dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder out = new StringBuilder();
        int len = dic.length();
        while (num>0){
            out.insert(0, dic.charAt(num % len));
            num = num/len;
        }
        return out.toString();
    }
}
