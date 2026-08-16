package com.railiq.entity;

import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "STATION")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Station {

    @Id
    @Column(name = "STATION_CODE", length = 15)
    private String stationCode;

    @Column(name = "STATION_NAME", length = 100)   // was wrongly "STATION_CODE"
    private String stationName;

    @Column(name = "LATITUDE")
    private Double latitude;

    @Column(name = "LONGITUDE")
    private Double longitude;

    @Column(name = "ZONE", length = 20)
    private String zone;

    @Column(name = "STATE", length = 50)
    private String state;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}