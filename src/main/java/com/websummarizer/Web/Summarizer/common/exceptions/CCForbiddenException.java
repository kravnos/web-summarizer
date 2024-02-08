package com.websummarizer.Web.Summarizer.common.exceptions;

import org.springframework.http.HttpStatus;

public class CCForbiddenException extends CCServerException {

    private static final HttpStatus httpStatus = HttpStatus.FORBIDDEN;

    public CCForbiddenException() {
        super(httpStatus);
    }

    public CCForbiddenException(String message) {
        super(message, httpStatus);
    }

    public CCForbiddenException(IError error) {
        super(error, httpStatus);
    }
}
