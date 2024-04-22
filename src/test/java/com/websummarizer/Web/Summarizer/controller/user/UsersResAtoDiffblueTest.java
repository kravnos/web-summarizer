package com.websummarizer.Web.Summarizer.controller.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class UsersResAtoDiffblueTest {
    /**
     * Method under test: {@link UsersResAto#getTotalCount()}
     */
    @Test
    void testGetTotalCount() {
        // Arrange, Act and Assert
        assertEquals(0, (new UsersResAto(new ArrayList<>())).getTotalCount());
    }
}
