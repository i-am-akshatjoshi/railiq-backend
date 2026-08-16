package com.railiq.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "BOOKINGS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_ID")
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "TRAIN_NO", referencedColumnName = "TRAIN_NO", nullable = false),
        @JoinColumn(name = "TRIP_NUMBER", referencedColumnName = "TRIP_NUMBER", nullable = false)
    })
    private Train train;

    @Column(name = "JOURNEY_DATE", nullable = false)
    private LocalDate journeyDate;

    @Column(name = "CLASS", nullable = false, length = 5)
    private String travelClass;

    @Column(name = "QUOTA", length = 10)
    private String quota;

    @Column(name = "STATUS", length = 20)
    private String status;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}