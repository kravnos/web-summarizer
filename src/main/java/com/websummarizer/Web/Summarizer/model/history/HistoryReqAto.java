package com.websummarizer.Web.Summarizer.model.history;

import com.websummarizer.Web.Summarizer.model.ShortLink;
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
    private String linkURL;

    @NonNull
    @ToString.Include
    private ShortLink short_link;

    @NonNull
    @ToString.Include
    private LocalDateTime upload_time;

}