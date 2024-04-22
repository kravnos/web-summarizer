package com.websummarizer.Web.Summarizer.controller.history;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class HistoriesResAtoDiffblueTest {
    /**
     * Method under test: {@link HistoriesResAto#getTotalCount()}
     */
    @Test
    void testGetTotalCount() {
        // Arrange, Act and Assert
        assertEquals(0, (new HistoriesResAto(new ArrayList<>())).getTotalCount());
    }
}
