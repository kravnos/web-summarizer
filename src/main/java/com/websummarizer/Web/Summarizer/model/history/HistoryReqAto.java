package com.websummarizer.Web.Summarizer.model.history;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.HistoryContent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private List<HistoryContent> historyContents;

    @NonNull
    @ToString.Include
    private String linkURL;

    @NonNull
    @ToString.Include
    private String short_link;

    @NonNull
    @ToString.Include
    private LocalDateTime upload_time;

}
