package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.repo.HistoryRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class HistoryServiceTest {

    @Mock
    private HistoryRepo historyRepo;

    @InjectMocks
    private HistoryService historyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testFindAllHistory() {
        // Arrange
        long userId = 1L;
        List<History> expectedHistories = new ArrayList<>();
        when(historyRepo.findAllByUserId(userId)).thenReturn(expectedHistories);

        // Act
        List<History> histories = historyService.findAllHistory(userId);

        // Assert
        assertEquals(expectedHistories, histories);
    }

    @Test
    void testSave() {
        // Arrange
        History history = new History("historyContent");
        when(historyRepo.save(history)).thenReturn(history);

        // Act
        History savedHistory = historyService.save(history);

        // Assert
        assertEquals(history, savedHistory);
    }

//    @Test
//    void testFindHistoryId() {
//        // Arrange
//        long id = 1L;
//        List<History> histories = new ArrayList<>();
//        histories.add(new History(1L,"","",new LocalDateTime(),new User("email.com")));
//        when(historyRepo.findAll()).thenReturn(histories);
//
//        // Act
//        List<HistoryResAto> result = historyService.findHistoryId(id);
//
//        // Assert
//        assertEquals(1, result.size());
//    }
}
