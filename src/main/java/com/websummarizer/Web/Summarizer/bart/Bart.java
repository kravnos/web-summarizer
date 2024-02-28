package com.websummarizer.Web.Summarizer.bart;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class Bart {

    private final String API_URL;
    private final String AUTH_TOKEN;
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;
    private ResponseEntity<String> responseEntity;

    public Bart(@Value("${API_URL}") String apiUrl, @Value("${AUTH_TOKEN}") String authToken) {
        this.API_URL = apiUrl;
        this.AUTH_TOKEN = authToken;
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.restTemplate = new RestTemplate();
        this.headers.setBearerAuth(AUTH_TOKEN);
    }

    public String queryModelURL(@RequestParam String url){

        //  Gets URL from browser URL, extract paragraph values, then put into requestBody
        //  Example: localhost:8080/test?url=https://en.wikipedia.org/wiki/Brock_University  ==> can change this to other link to test

        String prompt;
        try {
            prompt = HTMLParser.parser(url);
            String requestBody = "{\"inputs\": \"" + prompt + "\"}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            headers.setBearerAuth(AUTH_TOKEN);

            responseEntity = restTemplate.exchange(
                    API_URL, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONArray jsonArray = new JSONArray(responseEntity.getBody());
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                return jsonObject.getString("summary_text");
            } else {
                return "Cannot summarize at the moment. Please try again later.";
            }
        } catch (HttpClientErrorException e) {
            // Log the error for debugging
            System.out.println("HTTP client error");
            return "Error: " + e.getRawStatusCode() + " " + e.getStatusText();
        } catch (Exception e) {
            // Handle other exceptions
            return "An error occurred. Please try again later.";
        }
    }

    public String queryModelText(String inputText) {
        try {
            String requestBody = "{\"inputs\": \"" + inputText + "\"}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_URL, HttpMethod.POST, requestEntity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                JSONArray jsonArray = new JSONArray(responseEntity.getBody());
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                return jsonObject.getString("summary_text");
            } else {
                return "Cannot summarize at the moment. Please try again later.";
            }
        } catch (HttpClientErrorException e) {
            // Log the error for debugging
            System.out.println("HTTP client error");
            return "Error: " + e.getRawStatusCode() + " " + e.getStatusText();
        } catch (Exception e) {
            // Handle other exceptions
            return "An error occurred. Please try again later.";
        }
    }

}
