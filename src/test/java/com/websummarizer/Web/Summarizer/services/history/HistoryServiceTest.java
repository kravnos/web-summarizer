package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class HistoryServiceTest {

    public HistoryService historyService;
    public HistoryReqAto historyReqAto;
    public User user;
    String str = "1986-04-08 12:30";
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

    public HistoryReqAto getHistoryReqAto() {
        this.addHistory();
        return historyReqAto;
    }

    @Test
    void addHistory() {
        historyService.addHistory(historyReqAto);
    }

    @Test
    void getHistory() {
    }

    @Test
    void updateHistory() {
    }

    @Test
    void deleteHistory() {
    }

    @Test
    void findAllHistory() {
    }

    @Test
    public History createhistory(History history){
        history.setHistoryContent("Enter history set here");
        history.setUser(user);
        history.setShortLink("shortlink.go");
        history.setUploadTime(dateTime);
        return history;
    }
}