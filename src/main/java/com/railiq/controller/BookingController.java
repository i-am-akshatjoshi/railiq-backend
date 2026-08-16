package com.railiq.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.railiq.dto.BookingRequest;
import com.railiq.dto.BookingResponse;
import com.railiq.entity.Booking;
import com.railiq.entity.Train;
import com.railiq.entity.User;
import com.railiq.repository.BookingRepository;
import com.railiq.repository.TrainRepository;
import com.railiq.repository.UserRepository;
import com.railiq.service.NotificationService;


@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private NotificationService notificationService;

    // POST /api/bookings - create a booking for the logged-in user
    @PostMapping
    public ResponseEntity<?> createBooking(@RequestBody BookingRequest request, Authentication authentication) {
        String username = authentication.getName();
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body("User not found");
        }

        Optional<Train> trainOpt = trainRepository.findByIdTrainNoAndIdTripNumber(
                request.getTrainNo(), request.getTripNumber());
        if (trainOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Train not found");
        }

        Booking booking = new Booking();
        booking.setUser(userOpt.get());
        booking.setTrain(trainOpt.get());
        booking.setJourneyDate(request.getJourneyDate());
        booking.setTravelClass(request.getTravelClass());
        booking.setQuota(request.getQuota());
        booking.setStatus("PENDING");
        booking.setCreatedAt(LocalDateTime.now());

        Booking saved = bookingRepository.save(booking);

        String pnr = "PNR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        notificationService.sendBookingConfirmation(
                userOpt.get().getEmail(),
                request.getTrainNo(),
                pnr,
                request.getJourneyDate().toString()
        );

        return ResponseEntity.ok(BookingResponse.fromEntity(saved));
    }

    // GET /api/bookings/my - all bookings for the logged-in user
    @GetMapping("/my")
    public List<BookingResponse> getMyBookings(Authentication authentication) {
        String username = authentication.getName();
        List<Booking> bookings = bookingRepository.findByUser_Username(username);
        return bookings.stream()
                .map(BookingResponse::fromEntity)
                .collect(Collectors.toList());
    }

}