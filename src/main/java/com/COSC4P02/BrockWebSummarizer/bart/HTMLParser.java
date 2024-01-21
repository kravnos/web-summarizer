package com.COSC4P02.BrockWebSummarizer.bart;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;


public class HTMLParser {
    public static String parser(String url) throws IOException {
        //  Get the url, selects every paragraph element, extract the text value of each paragraph
        try {
            Document doc = Jsoup.connect(url).get();
            Elements paragraphs = doc.select("p");
            String websiteData = "";
            for(Element p : paragraphs)
                websiteData += p.text();
            //System.out.println(website);
            return websiteData;
        } catch (UnknownHostException u){
            return "URL Error";
        }

    }
}
