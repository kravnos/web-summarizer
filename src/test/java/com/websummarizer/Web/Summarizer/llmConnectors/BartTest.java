package com.websummarizer.Web.Summarizer.llmConnectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Bart.class, String.class})
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BartTest {

    @Value("${API_URL_BART}")
    private String apiUrl;
    @Value("${AUTH_TOKEN_BART}")
    private String authToken;

    /**
     * Method under test: {@link Bart#queryModel(String)}
     */
    @Test
    void testQueryModel(){
        Bart bart = new Bart(apiUrl, authToken);    // Need valid credentials in env.properties

        Assertions.assertNotEquals("Cannot summarize at the moment. Please try again later.", bart.queryModel("InputText"));
        Assertions.assertNotEquals("An error occurred. Please try again later.", bart.queryModel("InputText"));
        Assertions.assertFalse(bart.queryModel("InputText").startsWith("Error: "));
    }
}
