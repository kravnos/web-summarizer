package com.websummarizer.Web.Summarizer.bart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * This class provides a method to parse HTML content from a given URL and extract text from paragraph elements.
 */
public class HTMLParser {


    public static String parser(String url) {
        try {
            // Connect to the URL and get the HTML document
            Document doc = Jsoup.connect(url).get();

            // Select all paragraph elements from the HTML document
            Elements paragraphs = doc.select("p");

            // Use Java 8's Stream API to extract the text from each paragraph and join them into a single string
            String websiteData = paragraphs.stream()
                    .map(Element::text)
                    .collect(Collectors.joining(" "));

            // Return the concatenated text
            return websiteData.trim();
        } catch (IOException e) {
            // Log the exception and return a user-friendly message
            System.err.println("An error occurred while parsing the URL: " + e.getMessage());
            return "An error occurred. Please try again later.";
        }
    }

}

