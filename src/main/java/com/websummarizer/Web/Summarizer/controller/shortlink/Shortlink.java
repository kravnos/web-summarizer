package com.websummarizer.Web.Summarizer.controller.shortlink;

import com.websummarizer.Web.Summarizer.controller.BitLyController;
import com.websummarizer.Web.Summarizer.controller.HistoryController;
import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.services.users.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@RestController
public class Shortlink {
    @Autowired
    BitLyController bitLyController;

    @Autowired
    HistoryController historyController;

    @Autowired
    UserService userService;

    public String Shortlink(String url, String output, HttpSession session){

        String email = (String) session.getAttribute("username");
        Long id = userService.getFoundId(email);

        // Generate a short code for the given URL
        String ShortlinkCode = codeShort(url);
        // Create a new history request object with the generated short code
        HistoryReqAto historyReqAto = new HistoryReqAto(id, output, ShortlinkCode, LocalDateTime.now());
        // Add the history request to the database and get the response
        historyController.addHistory(historyReqAto);

        String shortBitly = bitLyController.processBitly("http://***.***.*.***:8080/"+ShortlinkCode); //need domain to make this function work,  else this is just the pc ip address to test

        return shortBitly;

    }

    // Method to generate a short code for a given URL
    public String codeShort(String urlOg){
        Random rand = new Random();
        urlOg = urlOg + rand.hashCode(); //add random avoiding duplicate code
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
