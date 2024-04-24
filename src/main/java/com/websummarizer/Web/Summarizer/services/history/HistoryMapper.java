package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.HistoryResAto;

public class HistoryMapper {

    // Map History entity to HistoryResAto
    public static HistoryResAto mapHistoryEtoResAto(History history) {
        return HistoryResAto.builder()
                .HID(history.getId())
                .UID(history.getUser().getId())
                .history_content(history.getHistoryContent())
                .short_link(String.valueOf(history.getShort_link()))
                .upload_time(history.getUploadTime())
                .build();
    }

}