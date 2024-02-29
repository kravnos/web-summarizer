package com.websummarizer.Web.Summarizer.bart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class HTMLParser {
    public static String parser(String url) throws IOException {
        // Get the URL, selects every paragraph element, extract the text value of each paragraph
        Document doc = Jsoup.connect(url).get();

        Elements paragraphs = doc.select("p");
        StringBuilder websiteData = new StringBuilder();
        for (Element p : paragraphs) {
            websiteData.append(p.text()).append(" ");
        }
        return websiteData.toString().trim();
    }
}

