package com.websummarizer.Web.Summarizer.controller.history;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Domain object for Affiliate (represents a row in table "history")
 */
@Getter
@Builder
@ToString( onlyExplicitlyIncluded = true)
/**
 * model representing API response model to add / register a history
 */
public class HistoryResAto {

    @NonNull
    @ToString.Include
    private Long HID;

    @NonNull
    @ToString.Include
    private Long UID;

    @NonNull
    @ToString.Include
    private String history_content;

    @NonNull
    @ToString.Include
    private String short_link;

    @NonNull
    @ToString.Include
    private LocalDateTime upload_time;

}

