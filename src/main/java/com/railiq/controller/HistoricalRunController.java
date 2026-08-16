package com.railiq.controller;

import com.railiq.dto.HistoricalRunResponse;
import com.railiq.entity.HistoricalRun;
import com.railiq.repository.HistoricalRunRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historical-runs")
public class HistoricalRunController {

    @Autowired
    private HistoricalRunRepository historicalRunRepository;

    // GET /api/historical-runs?page=0&size=20
    @GetMapping
    public Page<HistoricalRunResponse> getAllRuns(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return historicalRunRepository.findAll(PageRequest.of(page, size))
                .map(HistoricalRunResponse::fromEntity);
    }

    // GET /api/historical-runs/train/{trainNo}/{tripNumber}
    @GetMapping("/train/{trainNo}/{tripNumber}")
    public List<HistoricalRunResponse> getRunsForTrain(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber) {
        List<HistoricalRun> runs = historicalRunRepository.findByTrain_Id_TrainNoAndTrain_Id_TripNumber(trainNo, tripNumber);
        return runs.stream()
                .map(HistoricalRunResponse::fromEntity)
                .collect(Collectors.toList());
    }
}