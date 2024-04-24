package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.History;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoryRepoTest {

    @Mock
    private HistoryRepo historyRepo;

    @Test
    void testFindHistoryByShortLink() {
        // Arrange
        String shortLink = "example_short_link";
        History history = new History();
        when(historyRepo.findHistoryByShortLink(shortLink)).thenReturn(history);

        // Act
        History result = historyRepo.findHistoryByShortLink(shortLink);

        // Assert
        assertEquals(history, result);
    }

    @Test
    void testFindAllByUserId() {
        // Arrange
        Long userId = 1L;
        List<History> histories = new ArrayList<>();
        History history1 = new History();
        History history2 = new History();
        histories.add(history1);
        histories.add(history2);
        when(historyRepo.findAllByUserId(userId)).thenReturn(histories);

        // Act
        List<History> result = historyRepo.findAllByUserId(userId);

        // Assert
        assertEquals(2, result.size());
        assertEquals(history1, result.get(0));
        assertEquals(history2, result.get(1));
    }
}
