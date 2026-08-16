package com.railiq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TrainRecommendation {
    private String trainNo;
    private Integer tripNumber;
    private String trainName;
    private Double onTimePercentage;
    private Double avgDelayMinutes;
    private Double confirmationProbabilityPercent;
    private Double combinedScore;
}