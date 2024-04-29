package com.websummarizer.Web.Summarizer.repo;

import com.websummarizer.Web.Summarizer.model.History;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository interface for accessing history data in the database.
 */
public interface HistoryRepo extends CrudRepository<History, Long> {

    /**
     * Retrieves a list of history records based on the short link.
     *
     * @param shortLink The short link to search for.
     * @return A list of history records associated with the given short link.
     */
    @Query("SELECT h FROM History h WHERE h.short_link = ?1")
    List<History> findHistoryByShortLink(String shortLink);

    /**
     * Retrieves a list of all history records for a given user ID.
     *
     * @param userId The ID of the user to retrieve history records for.
     * @return A list of history records associated with the given user ID.
     */
    List<History> findAllByUserId(Long userId);
}
