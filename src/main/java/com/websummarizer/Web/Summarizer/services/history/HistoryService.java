package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.model.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.repo.HistoryRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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
    public List<HistoryResAto> getShortLink(String shortlink) {

        List<HistoryResAto> result = new ArrayList<HistoryResAto>();
        historyRepo.findHistoryByShortLink(shortlink).forEach((history) -> result.add(HistoryMapper.mapHistoryEtoResAto(history)));

        Collections.sort(result, new HistoryComparator());
        return result;
    }

}
