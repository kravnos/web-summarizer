package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoryRepository extends CrudRepository<History, Long> {
    //find user's history summarize using short link
    @Query("SELECT h FROM History WHERE h.shortlink = ?1")
    Optional<History> findHistoryByShortlink(String shortLink);
}

