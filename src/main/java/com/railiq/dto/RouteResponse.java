package com.railiq.dto;

public class RouteResponse {

    private Long routeId;

    private String trainNo;
    private Integer tripNumber;
    private String trainName;

    private String stationCode;
    private String stationName;

    private Integer stopSequence;
    private String scheduledArrival;
    private String scheduledDeparture;
    private Double distanceFromSourceKm;

    public RouteResponse() {
    }

    public RouteResponse(Long routeId, String trainNo, Integer tripNumber, String trainName,
                          String stationCode, String stationName, Integer stopSequence,
                          String scheduledArrival, String scheduledDeparture, Double distanceFromSourceKm) {
        this.routeId = routeId;
        this.trainNo = trainNo;
        this.tripNumber = tripNumber;
        this.trainName = trainName;
        this.stationCode = stationCode;
        this.stationName = stationName;
        this.stopSequence = stopSequence;
        this.scheduledArrival = scheduledArrival;
        this.scheduledDeparture = scheduledDeparture;
        this.distanceFromSourceKm = distanceFromSourceKm;
    }

    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

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

    public Integer getStopSequence() { return stopSequence; }
    public void setStopSequence(Integer stopSequence) { this.stopSequence = stopSequence; }

    public String getScheduledArrival() { return scheduledArrival; }
    public void setScheduledArrival(String scheduledArrival) { this.scheduledArrival = scheduledArrival; }

    public String getScheduledDeparture() { return scheduledDeparture; }
    public void setScheduledDeparture(String scheduledDeparture) { this.scheduledDeparture = scheduledDeparture; }

    public Double getDistanceFromSourceKm() { return distanceFromSourceKm; }
    public void setDistanceFromSourceKm(Double distanceFromSourceKm) { this.distanceFromSourceKm = distanceFromSourceKm; }

    // Builds a flat response from a Route entity - no nested Train/Station objects
    public static RouteResponse fromEntity(com.railiq.entity.Route route) {
        com.railiq.entity.Train train = route.getTrain();
        com.railiq.entity.Station station = route.getStation();

        return new RouteResponse(
                route.getRouteId(),
                train != null ? train.getId().getTrainNo() : null,
                train != null ? train.getId().getTripNumber() : null,
                train != null ? train.getTrainName() : null,
                station != null ? station.getStationCode() : null,
                station != null ? station.getStationName() : null,
                route.getStopSequence(),
                route.getScheduledArrival(),
                route.getScheduledDeparture(),
                route.getDistanceFromSourceKm()
        );
    }
}