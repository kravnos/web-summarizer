package com.websummarizer.Web.Summarizer.controller.shortlink;

import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@RestController
public class Shortlink {
    public Shortlink(){

    }

    // Method to generate a short code for a given URL
    public String codeShort(String urlOg){
        Random rand = new Random();
        urlOg = urlOg + rand.hashCode();
        int num = Math.abs(urlOg.hashCode());
        String dic = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String out = "";
        int len = dic.length();
        while (num>0){
            out = dic.charAt(num%len)+out;
            num = num/len;
        };
        return out;
    }
}
