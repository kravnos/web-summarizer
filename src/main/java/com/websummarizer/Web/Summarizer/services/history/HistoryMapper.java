package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.model.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.User;

public class HistoryMapper {

    // Map HistoryReqAto to History entity
    public static History mapHistoryReqAtoToEto(HistoryReqAto reqAto, User user) {
        return History.builder()
                .user(user)
                .historyContents(reqAto.getHistoryContents())
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
                .historyContents(history.getHistoryContents())
                .linkURL(history.getLinkURL())
                .short_link(history.getShortLink())
                .upload_time(history.getUploadTime())
                .build();
    }

    // Update fields of an existing History entity
    public static void updateHistory(History history, User user, HistoryReqAto update) {
        history.setUser(user);
        history.setHistoryContents(update.getHistoryContents());
        history.setLinkURL(update.getLinkURL());
        history.setShortLink(update.getShort_link());
        history.setUploadTime(update.getUpload_time());
    }

}
