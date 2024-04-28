package com.websummarizer.Web.Summarizer.configs;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // Handle all exceptions
    public ModelAndView handleException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView("error"); // Assuming "error" is your error page name
        modelAndView.addObject("errorMessage", ex.getMessage());
        return modelAndView;
    }
}

