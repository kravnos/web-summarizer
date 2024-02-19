package com.websummarizer.Web.Summarizer.common.exceptions;

import lombok.Getter;

/**
 * base exception class from which all other exceptions are extended
 */
@Getter
public abstract class CCBaseException extends RuntimeException{
    private String message;

    private IError error;

    public CCBaseException(Object errorParam) {
        if (errorParam instanceof IError) {
            this.error = (IError) errorParam;
        } else {
            this.message = (String) errorParam;
        }
    }
}
