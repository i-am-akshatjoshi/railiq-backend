package com.railiq.controller;

import com.railiq.dto.HistoricalBookingResponse;
import com.railiq.entity.HistoricalBooking;
import com.railiq.repository.HistoricalBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/historical-bookings")
public class HistoricalBookingController {

    @Autowired
    private HistoricalBookingRepository historicalBookingRepository;

    // GET /api/historical-bookings?page=0&size=20
    @GetMapping
    public Page<HistoricalBookingResponse> getAllBookings(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return historicalBookingRepository.findAll(PageRequest.of(page, size))
                .map(HistoricalBookingResponse::fromEntity);
    }

    // GET /api/historical-bookings/train/{trainNo}/{tripNumber}
    @GetMapping("/train/{trainNo}/{tripNumber}")
    public List<HistoricalBookingResponse> getBookingsForTrain(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber) {
        List<HistoricalBooking> bookings = historicalBookingRepository.findByTrain_Id_TrainNoAndTrain_Id_TripNumber(trainNo, tripNumber);
        return bookings.stream()
                .map(HistoricalBookingResponse::fromEntity)
                .collect(Collectors.toList());
    }
}