package com.railiq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class TrainReliability {
    private String trainNo;
    private Integer tripNumber;
    private String trainName;
    private Double avgDelayMinutes;
    private Long totalRuns;
    private Long onTimeRuns;
    private Double onTimePercentage;
}