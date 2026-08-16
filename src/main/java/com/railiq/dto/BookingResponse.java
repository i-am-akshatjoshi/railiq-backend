package com.railiq.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BookingResponse {

    private Long bookingId;
    private String username;

    private String trainNo;
    private Integer tripNumber;
    private String trainName;

    private String sourceStationCode;
    private String sourceStationName;
    private String destStationCode;
    private String destStationName;

    private Double distanceKm;
    private Integer durationMinutes;

    private LocalDate journeyDate;
    private String travelClass;
    private String quota;
    private String status;
    private LocalDateTime createdAt;

    public BookingResponse() {
    }

    public BookingResponse(Long bookingId, String username, String trainNo, Integer tripNumber,
                            String trainName, String sourceStationCode, String sourceStationName,
                            String destStationCode, String destStationName, Double distanceKm,
                            Integer durationMinutes, LocalDate journeyDate, String travelClass,
                            String quota, String status, LocalDateTime createdAt) {
        this.bookingId = bookingId;
        this.username = username;
        this.trainNo = trainNo;
        this.tripNumber = tripNumber;
        this.trainName = trainName;
        this.sourceStationCode = sourceStationCode;
        this.sourceStationName = sourceStationName;
        this.destStationCode = destStationCode;
        this.destStationName = destStationName;
        this.distanceKm = distanceKm;
        this.durationMinutes = durationMinutes;
        this.journeyDate = journeyDate;
        this.travelClass = travelClass;
        this.quota = quota;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getBookingId() { return bookingId; }
    public void setBookingId(Long bookingId) { this.bookingId = bookingId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

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

    public Double getDistanceKm() { return distanceKm; }
    public void setDistanceKm(Double distanceKm) { this.distanceKm = distanceKm; }

    public Integer getDurationMinutes() { return durationMinutes; }
    public void setDurationMinutes(Integer durationMinutes) { this.durationMinutes = durationMinutes; }

    public LocalDate getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }

    public String getTravelClass() { return travelClass; }
    public void setTravelClass(String travelClass) { this.travelClass = travelClass; }

    public String getQuota() { return quota; }
    public void setQuota(String quota) { this.quota = quota; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    // Builds a safe response from a Booking entity - no password hash, no nested bloat
    public static BookingResponse fromEntity(com.railiq.entity.Booking booking) {
        com.railiq.entity.Train train = booking.getTrain();
        com.railiq.entity.Station source = train.getSourceStation();
        com.railiq.entity.Station dest = train.getDestStation();

        return new BookingResponse(
                booking.getBookingId(),
                booking.getUser().getUsername(),
                train.getId().getTrainNo(),
                train.getId().getTripNumber(),
                train.getTrainName(),
                source.getStationCode(),
                source.getStationName(),
                dest.getStationCode(),
                dest.getStationName(),
                train.getDistanceKm(),
                train.getDurationMinutes(),
                booking.getJourneyDate(),
                booking.getTravelClass(),
                booking.getQuota(),
                booking.getStatus(),
                booking.getCreatedAt()
        );
    }
}