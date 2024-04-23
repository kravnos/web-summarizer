package com.websummarizer.Web.Summarizer.model.history;

import lombok.*;

import java.time.LocalDateTime;

/**
 * model representing API request model to add / register a "history"
 */
@Getter
@Builder
@AllArgsConstructor
@ToString( onlyExplicitlyIncluded = true)
public class HistoryReqAto {

    @NonNull
    @ToString.Include
    private Long UID;

    @NonNull
    @ToString.Include
    private String history_content;

    @ToString.Include
    private String linkURL;

    @ToString.Include
    private String shortLink;

    @NonNull
    @ToString.Include
    private LocalDateTime upload_time;

}