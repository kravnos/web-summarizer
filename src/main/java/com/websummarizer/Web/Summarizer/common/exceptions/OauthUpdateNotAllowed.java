package com.websummarizer.Web.Summarizer.common.exceptions;

public class OauthUpdateNotAllowed extends RuntimeException{
    @Override
    public String getMessage() {
        return "Oauth user is not allowed to change the credentials";
    }
}
