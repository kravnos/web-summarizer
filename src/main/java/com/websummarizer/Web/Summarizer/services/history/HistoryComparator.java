package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.model.HistoryResAto;

public class HistoryComparator implements java.util.Comparator<HistoryResAto> {
    @Override
    public int compare(HistoryResAto o1,HistoryResAto o2) {
        return o2.getUpload_time().compareTo(o1.getUpload_time());
    }
}