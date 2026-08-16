package com.railiq.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "HISTORICAL_BOOKINGS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HistoricalBooking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOOKING_HIST_ID")
    private Long bookingHistId;

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

    @Column(name = "DAYS_BEFORE_JOURNEY")
    private Integer daysBeforeJourney;

    @Column(name = "INITIAL_WL_POSITION")
    private Integer initialWlPosition;

    @Column(name = "FINAL_STATUS", length = 20)
    private String finalStatus;
}