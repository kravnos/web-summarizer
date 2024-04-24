package com.websummarizer.Web.Summarizer.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {ShortLinkGenerator.class})
@ExtendWith(SpringExtension.class)
class ShortLinkGeneratorTest {
    @Autowired
    private ShortLinkGenerator shortLinkGenerator;

    /**
     * Method under test: {@link ShortLinkGenerator#generateShortUrl()}
     */
    @Test
    void testGenerateShortUrl() {
        // Arrange and Act
        shortLinkGenerator.generateShortUrl();
    }
}
