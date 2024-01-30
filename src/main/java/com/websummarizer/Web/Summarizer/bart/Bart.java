package com.websummarizer.Web.Summarizer.bart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Bart {


    @Value("${API_URL}")
    private String API_URL;
    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN;

    HttpHeaders headers;

    public Bart(){
        //add code for connection handler
        headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

    }

    @RequestMapping("/test")
    public String queryModel(String inputText){
        String requestBody = "{\"inputs\": \"" + inputText + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(API_URL,requestEntity,String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            System.out.println("Error: " + responseEntity.getStatusCodeValue());
            return null;
        }
    }
}
