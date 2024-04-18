package com.websummarizer.Web.Summarizer.bart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
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
public class OpenAi {
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
        this.authToken = authToken;

        // Set up HTTP headers
        this.headers = new HttpHeaders();
        this.headers.setContentType(MediaType.APPLICATION_JSON);
        this.headers.setBearerAuth(authToken);

        // Initialize RestTemplate for making HTTP requests
        this.restTemplate = new RestTemplate();
    }

    // Method for querying the OpenAI model
    public String queryModel(String prompt) {
        try {
            // Create URL object for API endpoint
            URL obj = new URL(API_URL);
            // Open connection to the API endpoint
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            // Set HTTP request method to POST
            connection.setRequestMethod("POST");
            // Set authorization header with authentication token
            connection.setRequestProperty("Authorization", "Bearer " + this.authToken);
            // Set content type header
            connection.setRequestProperty("Content-Type", "application/json");

            // Construct the request body in JSON format
            String body = "{" +
                    "\"model\": \"" + model + "\", \"messages\": " +
                    "[" +
                    "{" +
                    "\"role\": \"system\"," +
                    " \"content\": \"" + "Summarize content you are provided with in short." + "\"}," +
                    "{\"role\": \"user\"," +
                    " \"content\": \"" + prompt + "\"}" +
                    "]" +
                    "}";
            // Allow output to connection
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            // Write request body to connection output stream
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from the OpenAI API
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuffer response = new StringBuffer();

                // Read response from the connection
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                // Log the response
                LOGGER.log(Level.INFO, "Response: " + response);

                // Extract the message from the JSON response
                return extractMessageFromJSONResponse(response.toString());
            } else {
                LOGGER.log(Level.WARNING, "Failed to query model. Response code: " + responseCode);
                // Throw exception if request fails
                throw new RuntimeException("Failed to query model. Response code: " + responseCode);
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Exception occurred while querying model", e);
            // Throw exception if an error occurs during request
            throw new RuntimeException("Exception occurred while querying model", e);
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
