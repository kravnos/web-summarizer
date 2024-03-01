package com.websummarizer.Web.Summarizer.bart;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class Bart {


    @Value("${API_URL}")
    private String API_URL="https://api-inference.huggingface.co/models/facebook/bart-large-cnn";
    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN="_________________API_TOKEN";

    HttpHeaders headers;

    public Bart(){

    }
    public String queryModel(@RequestParam String url){


        //  Gets URL from browser URL, extract paragraph values, then put into requestBody
        //  Example: localhost:8080/test?url=https://en.wikipedia.org/wiki/Brock_University  ==> can change this to other link to test
        String prompt = HTMLParser.parser(url);
        if (prompt.equals("URL Error")){
            return "There was a problem with the URL you input";
        }

        String requestBody = "{\"inputs\": \"" + prompt + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);


        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                API_URL, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONArray jsonArray = new JSONArray(responseEntity.getBody());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            return jsonObject.getString("summary_text");
        } else {
            return "Cannot summarize at the moment. Please try again later.";
        }
    }

    public String queryModelText(String inputText){

        //add code for connection handler
        headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

        String requestBody = "{\"inputs\": \"" + inputText + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.exchange(
                API_URL, HttpMethod.POST, requestEntity, String.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            JSONArray jsonArray = new JSONArray(responseEntity.getBody());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            return jsonObject.getString("summary_text");
        } else {
            return "Cannot summarize at the moment. Please try again later.";
        }
    }
}
