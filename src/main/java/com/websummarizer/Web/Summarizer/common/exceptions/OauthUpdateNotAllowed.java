package com.websummarizer.Web.Summarizer.common.exceptions;

/**
 * Exception indicating that an OAuth user is not allowed to change the credentials.
 */
public class OauthUpdateNotAllowed extends RuntimeException {

    /**
     * Returns the exception message.
     *
     * @return String containing the exception message
     */
    @Override
    public String getMessage() {
        return "Oauth user is not allowed to change the credentials";
    }
}
