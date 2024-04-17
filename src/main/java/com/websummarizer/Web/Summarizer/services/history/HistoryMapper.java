package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;

public class HistoryMapper {

    // Map HistoryReqAto to History entity
    public static History mapHistoryReqAtoToEto(HistoryReqAto reqAto, User user) {
        return History.builder()
                .user(user)
                .historyContent(reqAto.getHistory_content())
                .linkURL(reqAto.getLinkURL())
                .shortLink(reqAto.getShort_link())
                .uploadTime(reqAto.getUpload_time())
                .build();
    }

    // Map History entity to HistoryResAto
    public static HistoryResAto mapHistoryEtoResAto(History history) {
        return HistoryResAto.builder()
                .HID(history.getId())
                .UID(history.getUser().getId())
                .history_content(history.getHistoryContent())
                .linkURL(history.getLinkURL())
                .short_link(history.getShortLink())
                .upload_time(history.getUploadTime())
                .build();
    }

    // Update fields of an existing History entity
    public static void updateHistory(History history, User user, HistoryReqAto update) {
        history.setUser(user);
        history.setHistoryContent(update.getHistory_content());
        history.setLinkURL(update.getLinkURL());
        history.setShortLink(update.getShort_link());
        history.setUploadTime(update.getUpload_time());
    }

}
