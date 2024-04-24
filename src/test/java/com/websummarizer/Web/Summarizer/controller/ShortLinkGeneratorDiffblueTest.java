package com.websummarizer.Web.Summarizer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShortLinkGenerator.class})
@ExtendWith(SpringExtension.class)
class ShortLinkGeneratorDiffblueTest {
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;

    /**
     * Method under test: {@link ShortLinkGenerator#generateShortUrl()}
     */
    @Test
    void testGenerateShortUrl() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Missing observers.
        //   Diffblue Cover was unable to create an assertion.
        //   Add getters for the following fields or make them package-private:
        //     ShortLinkGenerator.webAddress

        // Arrange and Act
        shortLinkGenerator.generateShortUrl();
    }
}
