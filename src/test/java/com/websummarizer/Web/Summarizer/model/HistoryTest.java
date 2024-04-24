package com.websummarizer.Web.Summarizer.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HistoryTest {

    @Test
    public void testHistoryConstructorAndGetters() {
        // Create a User
        User user = new User();
        user.setId(1L);
        user.setEmail("example@example.com");
        user.setPassword("password");

        // Create a History
        LocalDateTime uploadTime = LocalDateTime.now();
        History history = new History("Test History");
        history.setId(1L);
        history.setInputText("Test Input Text");
        history.setShort_link("Test Short Link");
        history.setUploadTime(uploadTime);
        history.setUser(user);

        // Test constructor
        assertNotNull(history);
        assertEquals("Test History", history.getHistoryContent());

        // Test getters
        assertEquals(1L, history.getId());
        assertEquals("Test Input Text", history.getInputText());
        assertEquals("Test Short Link", history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }

    @Test
    public void testHistorySetters() {
        // Create a History
        History history = new History();

        // Set values using setters
        LocalDateTime uploadTime = LocalDateTime.now();
        User user = new User();
        user.setId(2L);
        user.setEmail("test@example.com");
        user.setPassword("password");

        history.setId(2L);
        history.setHistoryContent("Test Content");
        history.setInputText("Test Input Text");
        history.setShort_link("Test Short Link");
        history.setUploadTime(uploadTime);
        history.setUser(user);

        // Test getters to ensure values are set correctly
        assertEquals(2L, history.getId());
        assertEquals("Test Content", history.getHistoryContent());
        assertEquals("Test Input Text", history.getInputText());
        assertEquals("Test Short Link", history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }
    @Test
    public void testHistoryBuilder() {
        // Create a User
        User user = new User();
        user.setId(1L);
        user.setEmail("example@example.com");
        user.setPassword("password");

        // Create a History using the builder
        LocalDateTime uploadTime = LocalDateTime.now();
        History history = History.builder()
                .id(1L)
                .historyContent("Test History")
                .inputText("Test Input Text")
                .short_link("Test Short Link")
                .uploadTime(uploadTime)
                .user(user)
                .build();

        // Test builder-generated instance
        assertEquals(1L, history.getId());
        assertEquals("Test History", history.getHistoryContent());
        assertEquals("Test Input Text", history.getInputText());
        assertEquals("Test Short Link", history.getShort_link());
        assertEquals(uploadTime, history.getUploadTime());
        assertEquals(user, history.getUser());
    }
}
