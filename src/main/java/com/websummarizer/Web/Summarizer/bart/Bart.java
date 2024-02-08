package com.websummarizer.Web.Summarizer.bart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@RestController
public class Bart {


    @Value("${API_URL}")
    private String API_URL;
    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN = " ________________ API TOKEN __________________";

    HttpHeaders headers;

    public Bart(){
        //add code for connection handler

    }

    @RequestMapping("/test")
    public String queryModel(@RequestParam String url) throws IOException {
        // ==== Moved from constructor ====
        headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);
        // ================================
        System.out.println(AUTH_TOKEN);

        //  Gets URL from browser URL, extract paragraph values, then put into requestBody
        //  Example: localhost:8080/test?url=https://en.wikipedia.org/wiki/Brock_University  ==> can change this to other link to test
        String prompt = HTMLParser.parser(url);
        if (prompt.equals("URL Error")){
            return "There was a problem with the URL you input";
        }

        String requestBody = "{\"inputs\": \"" + prompt + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
        System.out.println(requestEntity.getHeaders());
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL,requestEntity,String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle the error case, e.g., log the error or throw an exception
            System.out.println("Error: " + responseEntity.getStatusCodeValue());
            return null;
        }
    }
}