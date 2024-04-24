package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class HistoryTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Long id = 1L;
        String historyContent = "Test history content";
        String shortLink = "shortLink";
        LocalDateTime uploadTime = LocalDateTime.now();
        User user = new User();

        // Act
        History history = new History(historyContent);
        history.setId(id);
        history.setShort_link(shortLink);
        history.setUploadTime(uploadTime);
        history.setUser(user);

        // Assert
        assertEquals(id, history.getId());
        assertEquals(historyContent, history.getHistoryContent());
        assertEquals(shortLink, history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        // Arrange & Act
        History history = new History();

        // Assert
        assertNotNull(history);
    }

    @Test
    void testSetterAndGetters() {
        // Arrange
        History history = new History();
        String historyContent = "Test history content";
        String shortLink = "shortLink";
        LocalDateTime uploadTime = LocalDateTime.now();
        User user = new User();

        // Act
        history.setHistoryContent(historyContent);
        history.setShort_link(shortLink);
        history.setUploadTime(uploadTime);
        history.setUser(user);

        // Assert
        assertEquals(historyContent, history.getHistoryContent());
        assertEquals(shortLink, history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }
    @Test
    void testAllArgsConstructor() {
        // Arrange
        Long id = 1L;
        String historyContent = "Test history content";
        String shortLink = "shortLink";
        LocalDateTime uploadTime = LocalDateTime.now();
        User user = new User();

        // Act
        History history = new History(id, historyContent, shortLink, uploadTime, user);

        // Assert
        assertNotNull(history);
        assertEquals(id, history.getId());
        assertEquals(historyContent, history.getHistoryContent());
        assertEquals(shortLink, history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }

    @Test
    void testBuilder() {
        // Arrange
        Long id = 1L;
        String historyContent = "Test history content";
        String shortLink = "shortLink";
        LocalDateTime uploadTime = LocalDateTime.now();
        User user = new User();

        // Act
        History history = History.builder()
                .id(id)
                .historyContent(historyContent)
                .short_link(shortLink)
                .uploadTime(uploadTime)
                .user(user)
                .build();

        // Assert
        assertNotNull(history);
        assertEquals(id, history.getId());
        assertEquals(historyContent, history.getHistoryContent());
        assertEquals(shortLink, history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }
}
