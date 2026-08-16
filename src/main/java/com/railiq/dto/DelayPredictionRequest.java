package com.railiq.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DelayPredictionRequest {
    private Double distanceKm;
    private Integer durationMinutes;
    private String trainType;
    private String dayOfWeek;
    private Integer isFestivalSeason;
    private Double weatherRiskScore;
}