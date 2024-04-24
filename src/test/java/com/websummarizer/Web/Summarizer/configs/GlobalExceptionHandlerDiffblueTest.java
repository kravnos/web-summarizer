package com.websummarizer.Web.Summarizer.configs;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

class GlobalExceptionHandlerDiffblueTest {
    /**
     * Method under test: {@link GlobalExceptionHandler#handleException(Exception)}
     */
    @Test
    void testHandleException() {
        // Arrange
        GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();

        // Act
        ModelAndView actualHandleExceptionResult = globalExceptionHandler.handleException(new Exception("foo"));

        // Assert
        assertNull(actualHandleExceptionResult.getView());
        Map<String, Object> expectedModelMap = actualHandleExceptionResult.getModel();
        assertSame(expectedModelMap, actualHandleExceptionResult.getModelMap());
    }
}
