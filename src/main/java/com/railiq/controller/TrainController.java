package com.railiq.controller;

import com.railiq.dto.TrainResponse;
import com.railiq.entity.Train;
import com.railiq.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/trains")
public class TrainController {

    @Autowired
    private TrainRepository trainRepository;

    // GET /api/trains?page=0&size=20 - paginated list, not all 16k at once
    @GetMapping
    public Page<TrainResponse> getAllTrains(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return trainRepository.findAll(pageable).map(TrainResponse::fromEntity);
    }

    @GetMapping("/{trainNo}")
    public List<TrainResponse> getTrainByNumber(@PathVariable String trainNo) {
        List<Train> trains = trainRepository.findByIdTrainNo(trainNo);
        return trains.stream()
                .map(TrainResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @GetMapping("/{trainNo}/{tripNumber}")
    public ResponseEntity<TrainResponse> getTrainByNumberAndTrip(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber) {
        Optional<Train> train = trainRepository.findByIdTrainNoAndIdTripNumber(trainNo, tripNumber);
        return train.map(t -> ResponseEntity.ok(TrainResponse.fromEntity(t)))
                     .orElse(ResponseEntity.notFound().build());
    }
}