package com.websummarizer.Web.Summarizer.bart;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.MalformedURLException;

import static org.junit.jupiter.api.Assertions.*;

class HTMLParserTest {
//    @Test
//    void testParser() throws IOException {
//        String url = "http://example.com"; // Replace with a URL for testing
//        String expected = "This domain is for use in illustrative examples in documents." +
//                " You may use this domain in literature without prior coordination or asking for permission." +
//                " More information..."; // Replace with the expected result
//
//        String result = HTMLParser.parser(url);
//        assertEquals(expected, result, "The parser did not return the expected result.");
//    }

    @Test
    public void testParserWithIOException() {
        String url = "invalid-url"; // Invalid URL that will cause an IOException
        assertThrows(IllegalArgumentException.class, () -> {
            HTMLParser.parser(url);
        });
    }

    @Test
    public void testParserWithNullURL() {
        String url = null; // Invalid URL that will cause an IOException
        assertThrows(IllegalArgumentException.class, () -> {
            HTMLParser.parser(url);
        });
    }






}