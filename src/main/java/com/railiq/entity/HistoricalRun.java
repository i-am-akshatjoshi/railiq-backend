package com.railiq.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "HISTORICAL_RUNS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class HistoricalRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RUN_ID")
    private Long runId;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "TRAIN_NO", referencedColumnName = "TRAIN_NO", nullable = false),
        @JoinColumn(name = "TRIP_NUMBER", referencedColumnName = "TRIP_NUMBER", nullable = false)
    })
    private Train train;

    @ManyToOne
    @JoinColumn(name = "STATION_CODE", referencedColumnName = "STATION_CODE", nullable = false)
    private Station station;

    @Column(name = "RUN_DATE", nullable = false)
    private LocalDate runDate;

    @Column(name = "SCHEDULED_ARRIVAL")
    private LocalDateTime scheduledArrival;

    @Column(name = "ACTUAL_ARRIVAL")
    private LocalDateTime actualArrival;

    @Column(name = "DELAY_MINUTES")
    private Integer delayMinutes;

    @Column(name = "DAY_OF_WEEK", length = 10)
    private String dayOfWeek;

    @Column(name = "IS_FESTIVAL_SEASON")
    private Integer isFestivalSeason;

    @Column(name = "WEATHER_RISK_SCORE")
    private Double weatherRiskScore;
}