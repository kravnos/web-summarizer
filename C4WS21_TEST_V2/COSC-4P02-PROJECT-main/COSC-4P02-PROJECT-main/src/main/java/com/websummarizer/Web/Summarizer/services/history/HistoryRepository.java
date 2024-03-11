package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoryRepository extends CrudRepository<History, Long> {

    @Query("SELECT h FROM History h WHERE h.shortLink = ?1")
    Optional<History> findHistoryByShortLink(String shortLink); //SELECT * FROM history WHERE email = ...
}
