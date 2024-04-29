package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.repo.HistoryRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for history objects
 */
@Service
@Log4j2
public class HistoryService {

    @Autowired
    private HistoryRepo historyRepo;

    /**
     * Retrieve all history records for a user.
     *
     * @param uid The user ID.
     * @return List of History objects.
     */
    public List<History> findAllHistory(long uid) {
        return historyRepo.findAllByUserId(uid);
    }

    /**
     * Save a new history record.
     *
     * @param history The history object to save.
     * @return The saved History object.
     */
    public History save(History history) {
        return historyRepo.save(history); //todo fix
    }

    /**
     * Find all history records for a user by user ID and map them to HistoryResAto objects.
     *
     * @param id The user ID.
     * @return List of HistoryResAto objects.
     */
    public List<HistoryResAto> findHistoryId(long id) {
        List<HistoryResAto> result = new ArrayList<HistoryResAto>();

        for(var h: historyRepo.findAll()){
            HistoryResAto history = HistoryMapper.mapHistoryEtoResAto(h);
            if(history.getUID() == id){
                result.add(history);
            }
        }
        return result;
    }

    /**
     * Get history records by shortlink.
     *
     * @param shortlink The shortlink.
     * @return List of History objects.
     */
    public List<History> getShortLink(String shortlink) {
        // Find the history in the repository by its shortlink
        // If the history is found, map it to a HistoryResAto object using HistoryMapper
        // Otherwise, throw a CCNotFoundException
        return historyRepo.findHistoryByShortLink(shortlink);
    }

}
