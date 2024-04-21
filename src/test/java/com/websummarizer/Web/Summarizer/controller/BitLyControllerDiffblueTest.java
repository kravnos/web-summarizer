package com.websummarizer.Web.Summarizer.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.websummarizer.Web.Summarizer.model.BitlyRequest;
import com.websummarizer.Web.Summarizer.services.BitlyService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BitLyController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class BitLyControllerDiffblueTest {
    @Autowired
    private BitLyController bitLyController;

    @MockBean
    private BitlyService bitlyService;

    /**
     * Method under test: {@link BitLyController#getBitly(String)}
     */
    @Test
    void testGetBitly() {
        // Arrange
        when(bitlyService.getShortURL(Mockito.<String>any())).thenReturn("https://example.org/example");

        // Act
        String actualBitly = bitLyController.getBitly("https://example.org/example");

        // Assert
        verify(bitlyService).getShortURL(eq("https://example.org/example"));
        assertEquals("https://example.org/example", actualBitly);
    }

    /**
     * Method under test: {@link BitLyController#processBitly(BitlyRequest)}
     */
    @Test
    void testProcessBitly() throws Exception {
        // Arrange
        when(bitlyService.getShortURL(Mockito.<String>any())).thenReturn("https://example.org/example");

        BitlyRequest bitlyRequest = new BitlyRequest();
        bitlyRequest.setLongURL("https://example.org/example");
        String content = (new ObjectMapper()).writeValueAsString(bitlyRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/processBitly")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(bitLyController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("https://example.org/example"));
    }
}
