package com.websummarizer.Web.Summarizer.common.exceptions;

import org.springframework.http.HttpStatus;

public class CCConflictException extends CCServerException{

    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public CCConflictException() {
        super(httpStatus);
    }

    public CCConflictException(String message) {
        super(message, httpStatus);
    }

    public CCConflictException(IError error) {
        super(error, httpStatus);
    }
}
