package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepo userRepo;

    @MockBean
    private HistoryService historyService;

    @MockBean
    private ShortLinkGenerator shortLinkGenerator;

    @Test
    public void testAddNewHistory() throws Exception {
        // Arrange
        User user = new User();
        user.setEmail("test@example.com");

        History history = new History();
        history.setUser(user);
        history.setInputText("Test Input");
        history.setHistoryContent("Test Output");
        history.setUploadTime(LocalDateTime.now());
        history.setShort_link("Test Short Link");

        when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.of(user));
        when(historyService.save(any(History.class))).thenReturn(history);

        // Act & Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/users/add-new-history")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"inputText\": \"Test Input\", \"output\": \"Test Output\", \"email\": \"test@example.com\"}"))
                .andExpect(status().isOk());
    }

        @Test
        public void testAddNewHistory_Success() throws Exception {
            // Arrange
            User user = new User();
            user.setEmail("test@example.com");

            History history = new History();
            history.setUser(user);
            history.setInputText("Test Input");
            history.setHistoryContent("Test Output");
            history.setUploadTime(LocalDateTime.now());
            history.setShort_link("Test Short Link");

            when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.of(user));
            when(historyService.save(any(History.class))).thenReturn(history);

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.post("/users/add-new-history")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"inputText\": \"Test Input\", \"output\": \"Test Output\", \"email\": \"test@example.com\"}"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testAddNewHistory_UserNotFound() throws Exception {
            // Arrange
            when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.empty());

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.post("/users/add-new-history")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"inputText\": \"Test Input\", \"output\": \"Test Output\", \"email\": \"test@example.com\"}"))
                    .andExpect(status().isOk());
        }

        @Test
        public void testAddNewHistory_SaveFailed() throws Exception {
            // Arrange
            User user = new User();
            user.setEmail("test@example.com");

            when(userRepo.findByEmail(any(String.class))).thenReturn(Optional.of(user));
            when(historyService.save(any(History.class))).thenThrow(new RuntimeException("Database error"));

            // Act & Assert
            mockMvc.perform(MockMvcRequestBuilders.post("/users/add-new-history")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("{\"inputText\": \"Test Input\", \"output\": \"Test Output\", \"email\": \"test@example.com\"}"))
                    .andExpect(status().isOk());
        }
}