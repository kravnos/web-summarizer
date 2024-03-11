package com.websummarizer.Web.Summarizer.services.history;

import com.websummarizer.Web.Summarizer.common.exceptions.CCNotFoundException;
import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoriesResAto;
import com.websummarizer.Web.Summarizer.model.History;
import com.websummarizer.Web.Summarizer.services.users.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Log4j2
public class HistoryService {

    @Autowired
    private  HistoryRepository historyRepository;

    @Autowired
    private UserRepository userRepository;

    // Add a new history record
    public HistoryResAto addHistory(HistoryReqAto reqSto) {
        var userOptional = userRepository.findById(reqSto.getUID());

        var historyResAto = userOptional.map(user -> {
            var history = historyRepository.save(HistoryMapper.mapHistoryReqAtoToEto(reqSto, user));
            return  HistoryMapper.mapHistoryEtoResAto(history);
        }).orElseThrow(() -> {
            log.error("No registered user found for histrory [history={}, userID={}]", reqSto, reqSto.getUID());
            throw  new CCNotFoundException("No registered user found for history");
        });

        return  historyResAto;
    }

    // Retrieve an existing history record
    public HistoryResAto getHistory(long id) {
        Optional<History> maybeFoundHistory = historyRepository.findById(id);
        return maybeFoundHistory.map(HistoryMapper::mapHistoryEtoResAto)
                .orElseThrow(CCNotFoundException::new);
    }

    // Update an existing history record
    public HistoryResAto updateHistory(Long id, HistoryReqAto historyReqAto) {

        Optional<History> maybeExisting = historyRepository.findById(id);

        return maybeExisting.map(history -> {
            HistoryMapper.updateHistory(history,
                    userRepository.findById(historyReqAto.getUID()).get(),
                    historyReqAto);

            historyRepository.save(history);
            return  HistoryMapper.mapHistoryEtoResAto(history);
        }).orElseThrow(CCNotFoundException::new);
    }

    // Delete a history record by its ID
    public void deleteHistory(long id) {
        if (!historyRepository.existsById(id)) {
            throw new CCNotFoundException(String.format("History record doesn't exist [id=%s]", id));
        }
        historyRepository.deleteById(id);
    }

    // Retrieve all history records
    public HistoriesResAto findAllHistory() {
        var historys = new ArrayList<HistoryResAto>();
        historyRepository.findAll().forEach((history) -> historys.add(HistoryMapper.mapHistoryEtoResAto(history)));

        return HistoriesResAto.builder().histories(historys).build();
    }

    // Get shortlink for a history
    public HistoryResAto getShortLink(String shortlink) {
        // Find the history in the repository by its shortlink
        Optional<History> maybeFoundHistory = historyRepository.findHistoryByShortlink(shortlink);
        // If the history is found, map it to a HistoryResAto object using HistoryMapper
        // Otherwise, throw a CCNotFoundException
        return maybeFoundHistory.map(HistoryMapper::mapHistoryEtoResAto)
                .orElseThrow(CCNotFoundException::new);
    }


}
