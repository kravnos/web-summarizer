package com.websummarizer.Web.Summarizer.controller.history;

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

    @NonNull
    @ToString.Include
    private String short_link;

    @NonNull
    @ToString.Include
    private LocalDateTime upload_time;

}
