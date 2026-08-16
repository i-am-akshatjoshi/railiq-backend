package com.railiq.controller;

import com.railiq.entity.Station;
import com.railiq.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/stations")
public class StationController {

    @Autowired
    private StationRepository stationRepository;

    // GET /api/stations?page=0&size=20
    @GetMapping
    public Page<Station> getAllStations(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return stationRepository.findAll(pageable);
    }

    // GET /api/stations/{stationCode}
    @GetMapping("/{stationCode}")
    public ResponseEntity<Station> getStationByCode(@PathVariable String stationCode) {
        return stationRepository.findById(stationCode)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}