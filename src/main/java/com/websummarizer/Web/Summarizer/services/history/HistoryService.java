package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.common.exceptions.CCNotFoundException;
import com.websummarizer.Web.Summarizer.model.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.model.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.repo.HistoryRepo;
import com.websummarizer.Web.Summarizer.repo.UserRepo;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class HistoryService {

    @Autowired
    private HistoryRepo historyRepo;

    @Autowired
    private UserRepo userRepository;

    // Add a new history record
    public HistoryResAto addHistory(HistoryReqAto reqSto) {
        var userOptional = userRepository.findById(reqSto.getUID());

        var historyResAto = userOptional.map(user -> {
            var history = historyRepo.save(HistoryMapper.mapHistoryReqAtoToEto(reqSto, user));
            return  HistoryMapper.mapHistoryEtoResAto(history);
        }).orElseThrow(() -> {
            log.error("No registered user found for histrory [history={}, userID={}]", reqSto, reqSto.getUID());
            throw  new CCNotFoundException("No registered user found for history");
        });

        return  historyResAto;
    }

    // Retrieve an existing history record
    public HistoryResAto getHistory(long id) {
        Optional<History> maybeFoundHistory = historyRepo.findById(id);
        return maybeFoundHistory.map(HistoryMapper::mapHistoryEtoResAto)
                .orElseThrow(CCNotFoundException::new);
    }

    // Update an existing history record
    public HistoryResAto updateHistory(Long id, HistoryReqAto historyReqAto) {

        Optional<History> maybeExisting = historyRepo.findById(id);

        return maybeExisting.map(history -> {
            HistoryMapper.updateHistory(history,
                    userRepository.findById(historyReqAto.getUID()).get(),
                    historyReqAto);

            historyRepo.save(history);
            return  HistoryMapper.mapHistoryEtoResAto(history);
        }).orElseThrow(CCNotFoundException::new);
    }

    // Delete a history record by its ID
    public void deleteHistory(long id) {
        if (!historyRepo.existsById(id)) {
            throw new CCNotFoundException(String.format("History record doesn't exist [id=%s]", id));
        }
        historyRepo.deleteById(id);
    }

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

}
