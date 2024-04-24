package com.websummarizer.Web.Summarizer.llmConnectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {Bart.class, String.class})
@ExtendWith(SpringExtension.class)
class BartDiffblueTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private Bart bart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void queryModel_Success() {
        // Arrange
        String inputText = "Test input";
        String summaryText = "Test summary";

        ResponseEntity<String> responseEntity = new ResponseEntity<>(
                "[{\"summary_text\": \"" + summaryText + "\"}]", HttpStatus.OK);

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(responseEntity);

        // Act
        String result = bart.queryModel(inputText);

        // Assert
        assertEquals(summaryText, result);
    }

    @Test
    void queryModel_Failure() {
        // Arrange
        String inputText = "Test input";

        when(restTemplate.exchange(any(String.class), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenThrow(new HttpClientErrorException(HttpStatus.BAD_REQUEST, "Bad Request"));

        // Act
        String result = bart.queryModel(inputText);

        // Assert
        assertEquals("An error occurred. Please try again later.", result);
    }
}
