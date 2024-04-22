package com.websummarizer.Web.Summarizer.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    //unfinished checkpassword function
    public boolean checkPassword(String password){
        String pattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";

        // Compile the pattern
        Pattern regex = Pattern.compile(pattern);

        // Create a Matcher object
        Matcher matcher = regex.matcher(password);
        return matcher.matches();
    }

//    //check password
//        if(!shortlink.checkPassword(password)) {
//        model.addAttribute("isValid", false);
//        model.addAttribute("html", "<span class=\"bi bi-exclamation-triangle-fill\"></span>");
//        model.addAttribute("message", "Password must contain at least 8 characters, 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character");
//
//        return "user/register";
//    }
}
