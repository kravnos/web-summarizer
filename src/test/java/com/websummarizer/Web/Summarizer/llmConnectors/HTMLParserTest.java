package com.websummarizer.Web.Summarizer.llmConnectors;

import com.websummarizer.Web.Summarizer.parsers.HTMLParser;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
class HTMLParserTest {
    @Test
    void testParser() {
        String url = "https://example.com";
        String expected = "This domain is for use in illustrative examples in documents." +
                " You may use this domain in literature without prior coordination or asking for permission." +
                " More information...";

        String result;
        try {
            result = HTMLParser.parser(url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, result, "The parser did not return the expected result.");
    }
    @Test
    public void testParserWithIOException() {
        String url = "invalid-url"; // Invalid URL that will cause an IOException
        assertThrows(IllegalArgumentException.class, () -> HTMLParser.parser(url));
    }
    @Test
    public void testParserWithNullURL() {
        String url = null; // Invalid URL that will cause an IOException
        assertThrows(IllegalArgumentException.class, () -> HTMLParser.parser(url));
    }

}