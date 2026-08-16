package com.railiq.controller;

import com.railiq.dto.RouteResponse;
import com.railiq.entity.Route;
import com.railiq.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/routes")
public class RouteController {

    @Autowired
    private RouteRepository routeRepository;

    @GetMapping("/{trainNo}/{tripNumber}")
    public List<RouteResponse> getRouteForTrain(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber) {
        List<Route> routes = routeRepository.findByTrain_Id_TrainNoAndTrain_Id_TripNumberOrderByStopSequenceAsc(trainNo, tripNumber);
        return routes.stream()
                .map(RouteResponse::fromEntity)
                .collect(Collectors.toList());
    }
}