package com.websummarizer.Web.Summarizer.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {BitlyService.class})
@ExtendWith(SpringExtension.class)
class BitlyServiceDiffblueTest {
    @Autowired
    private BitlyService bitlyService;

    /**
     * Method under test: {@link BitlyService#setup()}
     */
    @Test
    void testSetup() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Diffblue AI was unable to find a test

        // Arrange and Act
        bitlyService.setup();
    }

    /**
     * Method under test: {@link BitlyService#getShortURL(String)}
     */
    @Test
    void testGetShortURL() {
        // Arrange, Act and Assert
        assertEquals("error", bitlyService.getShortURL("https://example.org/example"));
    }
}
