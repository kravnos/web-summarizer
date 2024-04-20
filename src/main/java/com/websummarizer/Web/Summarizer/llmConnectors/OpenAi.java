package com.websummarizer.Web.Summarizer.llmConnectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
public class OpenAi implements Llm{
    // Logger for logging messages
    private static final Logger LOGGER = Logger.getLogger(OpenAi.class.getName());

    // URLs and parameters
    private final String API_URL; // Base URL for the OpenAI API
    private final HttpHeaders headers; // HTTP headers for API requests
    private final RestTemplate restTemplate; // Template for making HTTP requests
    private final String model; // Model to be used for processing
    private String authToken; // Authentication token for accessing the API

    // Constructor for initializing OpenAI object
    public OpenAi(@Value("${API_URL_OPENAI}") String apiUrl,
                  @Value("${AUTH_TOKEN_OPENAI}") String authToken,
                  @Value("${MODEL}")String model) {
        // Initialize instance variables with provided values
        this.API_URL = apiUrl;
        this.model = model;
        //this.authToken = authToken;

        // Set up HTTP headers
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.setBearerAuth(authToken);

        // Initialize RestTemplate for making HTTP requests
        this.restTemplate = new RestTemplate();
    }

    // Method for querying the OpenAI model
    @Override
    public String queryModel(String prompt) {
        try {
            // Prepare request body
            String requestBody = "{" +
                    "\"model\": \"" + model + "\", \"messages\": " +
                    "[" +
                    "{" +
                    "\"role\": \"system\"," +
                    " \"content\": \"" + "Summarize content you are provided with in short." + "\"}," +
                    "{\"role\": \"user\"," +
                    " \"content\": \"" + prompt + "\"}" +
                    "]" +
                    "}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send HTTP POST request to Bart API
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_URL, HttpMethod.POST, requestEntity, String.class);

            LOGGER.info("response entity from openai: " + responseEntity);

            // Check if request was successful
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Parse response JSON
                LOGGER.info("response ok");

                // Return the summarized text
                return extractMessageFromJSONResponse(responseEntity.toString());
            } else {
                // Return error message if request was not successful
                return "Cannot summarize at the moment. Please try again later.";
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log HTTP client/server error
            LOGGER.log(Level.SEVERE, "HTTP client/server error");
            return "Error: " + e.getStatusCode() + " " + e.getStatusText();
        } catch (Exception e) {
            // Log other exceptions
            LOGGER.log(Level.SEVERE, "An error occurred");
            return "An error occurred. Please try again later.";
        }
    }

    // Method for extracting message from JSON response
    public static String extractMessageFromJSONResponse(String response) {
        // Find the start and end index of the content in the response
        int start = response.indexOf("content") + 11;
        int end = response.indexOf("\"", start);
        // Extract and return the content
        return response.substring(start, end);
    }
}
