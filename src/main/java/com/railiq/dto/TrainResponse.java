package com.railiq.dto;

import java.time.LocalDateTime;

public class TrainResponse {

    private String trainNo;
    private Integer tripNumber;
    private String trainName;

    private String sourceStationCode;
    private String sourceStationName;
    private String destStationCode;
    private String destStationName;

    private String trainType;
    private String classesAvailable;
    private Double distanceKm;
    private Integer durationMinutes;
    private LocalDateTime createdAt;

    public TrainResponse() {
    }

    public TrainResponse(String trainNo, Integer tripNumber, String trainName,
                          String sourceStationCode, String sourceStationName,
                          String destStationCode, String destStationName,
                          String trainType, String classesAvailable, Double distanceKm,
                          Integer durationMinutes, LocalDateTime createdAt) {
        this.trainNo = trainNo;
        this.tripNumber = tripNumber;
        this.trainName = trainName;
        this.sourceStationCode = sourceStationCode;
        this.sourceStationName = sourceStationName;
        this.destStationCode = destStationCode;
        this.destStationName = destStationName;
        this.trainType = trainType;
        this.classesAvailable = classesAvailable;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.createdAt = createdAt;
    }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public Integer getTripNumber() { return tripNumber; }
    public void setTripNumber(Integer tripNumber) { this.tripNumber = tripNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public String getSourceStationCode() { return sourceStationCode; }
    public void setSourceStationCode(String sourceStationCode) { this.sourceStationCode = sourceStationCode; }

    public String getSourceStationName() { return sourceStationName; }
    public void setSourceStationName(String sourceStationName) { this.sourceStationName = sourceStationName; }

    public String getDestStationCode() { return destStationCode; }
    public void setDestStationCode(String destStationCode) { this.destStationCode = destStationCode; }

    public String getDestStationName() { return destStationName; }
    public void setDestStationName(String destStationName) { this.destStationName = destStationName; }

    public String getTrainType() { return trainType; }
    public void setTrainType(String trainType) { this.trainType = trainType; }

    public String getClassesAvailable() { return classesAvailable; }
    public void setClassesAvailable(String classesAvailable) { this.classesAvailable = classesAvailable; }

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Builds a flat response from a Train entity - no nested Station objects
    public static TrainResponse fromEntity(com.railiq.entity.Train train) {
        com.railiq.entity.Station source = train.getSourceStation();
        com.railiq.entity.Station dest = train.getDestStation();

        return new TrainResponse(
                train.getId().getTrainNo(),
                train.getId().getTripNumber(),
                train.getTrainName(),
                source != null ? source.getStationCode() : null,
                source != null ? source.getStationName() : null,
                dest != null ? dest.getStationCode() : null,
                dest != null ? dest.getStationName() : null,
                train.getTrainType(),
                train.getClassesAvailable(),
                train.getDistanceKm(),
                train.getDurationMinutes(),
                train.getCreatedAt()
        );
    }
}