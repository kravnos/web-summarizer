package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HistoryRepo extends CrudRepository<History, Long> {

    //find user's history summarize using short link
//    @Query("SELECT h FROM History h WHERE h.shortLink = ?1")
//    Optional<History> findHistoryByShortLink(String shortLink); //SELECT * FROM history WHERE email = ...
}
