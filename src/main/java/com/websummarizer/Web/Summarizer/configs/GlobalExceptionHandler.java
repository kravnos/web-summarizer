package com.websummarizer.Web.Summarizer.configs;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler to handle all exceptions thrown by controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles all exceptions and redirects to the error page.
     *
     * @param ex The exception to handle
     * @return ModelAndView object representing the error page
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        // Create a ModelAndView object for the error page
        ModelAndView modelAndView = new ModelAndView("error"); // Assuming "error" is your error page name

        // Add the error message to the model
        modelAndView.addObject("errorMessage", ex.getMessage());

        // Return the ModelAndView object
        return modelAndView;
    }
}
