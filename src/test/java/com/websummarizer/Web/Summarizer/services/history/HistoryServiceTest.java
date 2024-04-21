package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    public User createUser(User user) { //Test creating user
        user.setFirst_name("Test");
        user.setLast_name("Attempt");
        System.out.println("test");
        user.setEmail("test@gmail.com");
        user.setId(12345);
        user.setPassword("12345");
        System.out.println(user.getLast_name());
        return user;
    }

    @Test
    public History createhistory(History history){
        history.setHistoryContent("Enter history set here");
        history.setUser(user);
        history.setShortLink("shortlink.go");
        history.setUploadTime(dateTime);
        System.out.println(history.getShortLink());
        return history;
    }

    @Test
    void addHistory() {
        historyService.addHistory(historyReqAto);
    }

    @Test
    void getHistory() {
        historyService.getHistory(user.getId());
    }

    @Test
    void updateHistory() {
        historyService.updateHistory(user.getId(), historyReqAto);
    }

    @Test
    void deleteHistory() {
        historyService.deleteHistory(user.getId());
    }

    @Test
    void findAllHistory() {
        historyService.findAllHistory();
    }


}