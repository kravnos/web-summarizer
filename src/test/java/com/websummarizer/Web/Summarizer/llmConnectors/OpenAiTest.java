package com.websummarizer.Web.Summarizer.llmConnectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {OpenAi.class, String.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OpenAiTest {
    @Value("${API_URL_BART}")
    private String apiUrl;
    @Value("${AUTH_TOKEN_BART}")
    private String authToken;
    @Value("${MODEL}")
    private String model;

    /**
     * Method under test: {@link OpenAi#queryModel(String)}
     */
    @Test
    void testQueryModel(){
        OpenAi openAi = new OpenAi(apiUrl, authToken, model);   // Need valid credentials in env.properties

        Assertions.assertNotEquals("Cannot summarize at the moment. Please try again later.", openAi.queryModel("InputText"));
        Assertions.assertNotEquals("An error occurred. Please try again later.", openAi.queryModel("InputText"));
        Assertions.assertFalse(openAi.queryModel("InputText").startsWith("Error: "));
    }
}
