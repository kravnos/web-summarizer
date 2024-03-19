package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<History, Long> {
}
