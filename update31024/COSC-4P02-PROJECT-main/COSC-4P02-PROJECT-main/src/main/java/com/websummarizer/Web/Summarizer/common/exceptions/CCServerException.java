package com.websummarizer.Web.Summarizer.common.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CCServerException extends CCBaseException{
    private int statusCode;

    public CCServerException(HttpStatus status) {
        super(status.name());
        this.statusCode = status.value();
    }

    public CCServerException(IError error, HttpStatus status) {
        super(error);
        this.statusCode = status.value();
    }

    public CCServerException(String message, HttpStatus status) {
        super(message);
        this.statusCode = status.value();
    }
}
