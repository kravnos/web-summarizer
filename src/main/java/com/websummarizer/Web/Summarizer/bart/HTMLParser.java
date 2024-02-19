package com.websummarizer.Web.Summarizer.bart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLParser {

    public static String parser(String url) {
        try {
            // Get the URL, selects every paragraph element, extract the text value of each paragraph
            Document doc = (Document) Jsoup.connect(url).get();

            Elements paragraphs = doc.select("p");
            StringBuilder websiteData = new StringBuilder();
            for (Element p : paragraphs) {
                websiteData.append(p.text()).append(" ");
            }
            return websiteData.toString().trim();
        } catch (IOException e) {
            // Handle IOException (e.g., connection error)
            return "Request Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) throws IOException {
        // Example usage:
        String urlToParse = "https://edition.cnn.com/2024/01/29/politics/luttig-conway-supreme-court-trump-insurrection/index.html";
        String result2 = parser(urlToParse);
        System.out.println("mm2: " + result2);
    }
}

