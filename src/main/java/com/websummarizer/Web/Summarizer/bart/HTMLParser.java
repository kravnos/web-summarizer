package com.websummarizer.Web.Summarizer.bart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides a method to parse HTML content from a given URL and extract text from paragraph elements.
 */
public class HTMLParser {
    private static final Logger LOGGER = Logger.getLogger(HTMLParser.class.getName());

    /**
     * Parses HTML content from a given URL and extracts text from paragraph elements.
     *
     * @param url The URL of the webpage to parse.
     * @return A string containing the concatenated text from all paragraph elements found on the webpage.
     * @throws IOException If an I/O error occurs while connecting to the URL or reading its content.
     */
    public static String parser(String url) throws IOException {
        try {
            // Get the URL, selects every paragraph element, extract the text value of each paragraph
            Document doc = Jsoup.connect(url).get();

            // Select all paragraph elements from the HTML document
            Elements paragraphs = doc.select("p");

            // StringBuilder to store the extracted text from paragraph elements
            StringBuilder websiteData = new StringBuilder();

            // Iterate through each paragraph element and append its text to the StringBuilder
            for (Element p : paragraphs) {
                websiteData.append(p.text()).append(" ");
            }
            // Log success message
            LOGGER.log(Level.INFO, "HTML content parsed successfully from URL: {0}", url);

            // Trim excess whitespace and return the concatenated text
            return websiteData.toString().trim();
        } catch (IOException e) {
            // Log error message
            LOGGER.log(Level.SEVERE, "Error parsing HTML content from URL: " + url, e);
            throw e;
        }
    }
}

