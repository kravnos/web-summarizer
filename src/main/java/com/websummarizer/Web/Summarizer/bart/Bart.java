package com.websummarizer.Web.Summarizer.bart;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller class for handling Bart API requests.
 */
@RestController
public class Bart {
    private static final Logger LOGGER = Logger.getLogger(Bart.class.getName());
    private final String API_URL;
    private final HttpHeaders headers;
    private final RestTemplate restTemplate;

    /**
     * Constructor for Bart class.
     *
     * @param apiUrl    The URL of the Bart API.
     * @param authToken The authentication token for accessing the Bart API.
     */
    public Bart(@Value("${API_URL}") String apiUrl, @Value("${AUTH_TOKEN}") String authToken) {
        this.API_URL = apiUrl;

        // Set up HTTP headers
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.setBearerAuth(authToken);

        // Initialize RestTemplate for making HTTP requests
        this.restTemplate = new RestTemplate();
    }

    /**
     * Queries the Bart API to summarize input text.
     *
     * @param inputText The text to be summarized.
     * @return The summarized text, or an error message if summarization fails.
     */
    public String queryModel(String inputText) {
        try {
            // Prepare request body
            String requestBody = "{\"inputs\": \"" + inputText + "\"}";
            HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

            // Send HTTP POST request to Bart API
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    API_URL, HttpMethod.POST, requestEntity, String.class);

            // Check if request was successful
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                // Parse response JSON
                JSONArray jsonArray = new JSONArray(responseEntity.getBody());
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // Return the summarized text
                return jsonObject.getString("summary_text");
            } else {
                // Return error message if request was not successful
                return "Cannot summarize at the moment. Please try again later.";
            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Log HTTP client/server error
            LOGGER.log(Level.SEVERE, "HTTP client/server error");
            return "Error: " + e.getRawStatusCode() + " " + e.getStatusText();
        } catch (Exception e) {
            // Log other exceptions
            LOGGER.log(Level.SEVERE, "An error occurred");
            return "An error occurred. Please try again later.";
        }
    }
}
