package com.websummarizer.Web.Summarizer.controller.history;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@ToString( onlyExplicitlyIncluded = true)
public class HistoriesResAto {
    private List<HistoryResAto> histories;

    public int getTotalCount() {
        if (histories == null) {
            return 0;
        }
        return histories.size();
    }
}
