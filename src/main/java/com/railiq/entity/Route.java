package com.railiq.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ROUTES")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ROUTE_ID")
    private Long routeId;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "TRAIN_NO", referencedColumnName = "TRAIN_NO", nullable = false),
        @JoinColumn(name = "TRIP_NUMBER", referencedColumnName = "TRIP_NUMBER", nullable = false)
    })
    private Train train;

    @ManyToOne
    @JoinColumn(name = "STATION_CODE", referencedColumnName = "STATION_CODE", nullable = false)
    private Station station;

    @Column(name = "STOP_SEQUENCE", nullable = false)
    private Integer stopSequence;

    @Column(name = "SCHEDULED_ARRIVAL", length = 8)
    private String scheduledArrival;

    @Column(name = "SCHEDULED_DEPARTURE", length = 8)
    private String scheduledDeparture;

    @Column(name = "DISTANCE_FROM_SOURCE_KM")
    private Double distanceFromSourceKm;
}