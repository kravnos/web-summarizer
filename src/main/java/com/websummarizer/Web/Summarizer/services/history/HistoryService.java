package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.repo.HistoryRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class HistoryService {

    @Autowired
    private HistoryRepo historyRepo;

    // Retrieve all history records
    public List<History> findAllHistory(long uid) {
        return historyRepo.findAllByUserId(uid);
    }

    public History save(History history) {
        return historyRepo.save(history); //todo fix
    }

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

    // Get shortlink for a history
    public List<History> getShortLink(String shortlink) {
        // Find the history in the repository by its shortlink
        // If the history is found, map it to a HistoryResAto object using HistoryMapper
        // Otherwise, throw a CCNotFoundException
        return historyRepo.findHistoryByShortLink(shortlink);
    }

}
