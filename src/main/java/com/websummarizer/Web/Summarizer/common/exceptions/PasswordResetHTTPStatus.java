package com.websummarizer.Web.Summarizer.common.exceptions;

public class PasswordResetHTTPStatus {
    public static int OK() { return 200; }
    public static int INVALID_EMAIL() { return 350; }
    public static int EMAIL_NOT_FOUND() { return 351; }
    public static int INVALID_TOKEN() { return 352; }
    public static int SUCCESS_REDIRECT_TO_RESET_FORM() { return 353; }
    public static int EMAIL_PARSE_ERROR() { return 550; }

}
