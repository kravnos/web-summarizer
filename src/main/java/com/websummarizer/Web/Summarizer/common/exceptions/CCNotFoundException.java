package com.websummarizer.Web.Summarizer.common.exceptions;

import org.springframework.http.HttpStatus;

public final class CCNotFoundException extends CCServerException {

    private static final HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public CCNotFoundException() {
        super(httpStatus);
    }

    public CCNotFoundException(String message) {
        super(message, httpStatus);
    }

    public CCNotFoundException(IError error) {
        super(error, httpStatus);
    }
}
