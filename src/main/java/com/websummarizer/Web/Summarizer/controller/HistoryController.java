package com.websummarizer.Web.Summarizer.controller;

import com.websummarizer.Web.Summarizer.controller.history.HistoryReqAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoryResAto;
import com.websummarizer.Web.Summarizer.controller.history.HistoriesResAto;
import com.websummarizer.Web.Summarizer.services.history.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/history")
@Slf4j
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    // Add a new history record
    @PostMapping
    public ResponseEntity<HistoryResAto> addHistory(@RequestBody HistoryReqAto historyReqAto) {
        log.info("Add history request received : " + historyReqAto);
        var historyResAto = historyService.addHistory(historyReqAto);
        log.info("Add history request processed : " + historyReqAto);
        return ResponseEntity.ok(historyResAto);
    }

    // Delete a history record by ID
    @DeleteMapping("/{id}")
    public ResponseEntity deleteHistory(@PathVariable Long id) {
        log.info("Delete history request received : " + id);
        historyService.deleteHistory(id);
        log.info("Delete history request processed : " + id);
        return ResponseEntity.ok().build();
    }

    // Get a history record by ID
    @GetMapping("/{id}")
    public ResponseEntity<HistoryResAto> getHistory(@PathVariable Long id) {
        log.info("Get history request received : " + id);
        var historyResAto = historyService.getHistory(id);
        log.info("Get history request processed : " + historyResAto);
        return ResponseEntity.ok(historyResAto);
    }

    // Get all history records
    @GetMapping()
    public ResponseEntity<HistoriesResAto> findAllHistory() {
        log.info("Find all history request received");
        var allHistories = historyService.findAllHistory();
        log.info("Find all history request processed. Total count : " + allHistories.getTotalCount());
        return ResponseEntity.ok(allHistories);
    }

    // Update a history record by ID
    @PutMapping("/{id}")
    public ResponseEntity<HistoryResAto> updateHistory(@PathVariable Long id,
                                                       @RequestBody HistoryReqAto historyReqAto) {
        log.info(String.format("Update history request received [id=%s, req=%s]", id, historyReqAto));
        var historyResAto = historyService.updateHistory(id, historyReqAto);
        log.info("Update history request processed : " + historyResAto);
        return ResponseEntity.ok(historyResAto);
    }
}
