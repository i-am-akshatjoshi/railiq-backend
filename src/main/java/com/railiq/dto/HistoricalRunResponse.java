package com.railiq.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class HistoricalRunResponse {

    private Long runId;

    private String trainNo;
    private Integer tripNumber;
    private String trainName;

    private String stationCode;
    private String stationName;

    private LocalDate runDate;
    private LocalDateTime scheduledArrival;
    private LocalDateTime actualArrival;
    private Integer delayMinutes;
    private String dayOfWeek;
    private Integer isFestivalSeason;
    private Double weatherRiskScore;

    public HistoricalRunResponse() {
    }

    public HistoricalRunResponse(Long runId, String trainNo, Integer tripNumber, String trainName,
                                  String stationCode, String stationName, LocalDate runDate,
                                  LocalDateTime scheduledArrival, LocalDateTime actualArrival,
                                  Integer delayMinutes, String dayOfWeek, Integer isFestivalSeason,
                                  Double weatherRiskScore) {
        this.runId = runId;
        this.trainNo = trainNo;
        this.tripNumber = tripNumber;
        this.trainName = trainName;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.runDate = runDate;
        this.scheduledArrival = scheduledArrival;
        this.actualArrival = actualArrival;
        this.delayMinutes = delayMinutes;
        this.dayOfWeek = dayOfWeek;
        this.isFestivalSeason = isFestivalSeason;
        this.weatherRiskScore = weatherRiskScore;
    }

    public Long getRunId() { return runId; }
    public void setRunId(Long runId) { this.runId = runId; }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public Integer getTripNumber() { return tripNumber; }
    public void setTripNumber(Integer tripNumber) { this.tripNumber = tripNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getStationCode() { return stationCode; }
    public void setStationCode(String stationCode) { this.stationCode = stationCode; }

    public String getStationName() { return stationName; }
    public void setStationName(String stationName) { this.stationName = stationName; }

    public LocalDate getRunDate() { return runDate; }
    public void setRunDate(LocalDate runDate) { this.runDate = runDate; }

    public LocalDateTime getScheduledArrival() { return scheduledArrival; }
    public void setScheduledArrival(LocalDateTime scheduledArrival) { this.scheduledArrival = scheduledArrival; }

    public LocalDateTime getActualArrival() { return actualArrival; }
    public void setActualArrival(LocalDateTime actualArrival) { this.actualArrival = actualArrival; }

    public Integer getDelayMinutes() { return delayMinutes; }
    public void setDelayMinutes(Integer delayMinutes) { this.delayMinutes = delayMinutes; }

    public String getDayOfWeek() { return dayOfWeek; }
    public void setDayOfWeek(String dayOfWeek) { this.dayOfWeek = dayOfWeek; }

    public Integer getIsFestivalSeason() { return isFestivalSeason; }
    public void setIsFestivalSeason(Integer isFestivalSeason) { this.isFestivalSeason = isFestivalSeason; }

    public Double getWeatherRiskScore() { return weatherRiskScore; }
    public void setWeatherRiskScore(Double weatherRiskScore) { this.weatherRiskScore = weatherRiskScore; }

    // Builds a flat response from a HistoricalRun entity - no nested Train/Station objects
    public static HistoricalRunResponse fromEntity(com.railiq.entity.HistoricalRun run) {
        com.railiq.entity.Train train = run.getTrain();
        com.railiq.entity.Station station = run.getStation();

        return new HistoricalRunResponse(
                run.getRunId(),
                train != null ? train.getId().getTrainNo() : null,
                train != null ? train.getId().getTripNumber() : null,
                train != null ? train.getTrainName() : null,
                station != null ? station.getStationCode() : null,
                station != null ? station.getStationName() : null,
                run.getRunDate(),
                run.getScheduledArrival(),
                run.getActualArrival(),
                run.getDelayMinutes(),
                run.getDayOfWeek(),
                run.getIsFestivalSeason(),
                run.getWeatherRiskScore()
        );
    }
}