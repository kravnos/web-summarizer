package com.COSC4P02.BrockWebSummarizer.bart;

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
    //@Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN = " ________________ API TOKEN __________________";

    HttpHeaders headers;

    public Bart(){
        //add code for connection handler
        headers = new HttpHeaders();
        headers.setBearerAuth(AUTH_TOKEN);
        headers.setContentType(MediaType.APPLICATION_JSON);

    }

    @RequestMapping("/test")
    public String queryModel(String inputText){
        System.out.println(AUTH_TOKEN);
        inputText = "The tower is 324 metres (1,063 ft) tall, about the same height as an 81-storey building, and the tallest structure in Paris. Its base is square, measuring 125 metres (410 ft) on each side. During its construction, the Eiffel Tower surpassed the Washington Monument to become the tallest man-made structure in the world, a title it held for 41 years until the Chrysler Building in New York City was finished in 1930. It was the first structure to reach a height of 300 metres. Due to the addition of a broadcasting aerial at the top of the tower in 1957, it is now taller than the Chrysler Building by 5.2 metres (17 ft). Excluding transmitters, the Eiffel Tower is the second tallest free-standing structure in France after the Millau Viaduct.";
        String requestBody = "{\"inputs\": \"" + inputText + "\"}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody,headers);
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
