package com.railiq.dto;

import java.time.LocalDate;

public class HistoricalBookingResponse {

    private Long bookingHistId;

    private String trainNo;
    private Integer tripNumber;
    private String trainName;

    private LocalDate journeyDate;
    private String travelClass;
    private String quota;
    private Integer daysBeforeJourney;
    private Integer initialWlPosition;
    private String finalStatus;

    public HistoricalBookingResponse() {
    }

    public HistoricalBookingResponse(Long bookingHistId, String trainNo, Integer tripNumber, String trainName,
                                      LocalDate journeyDate, String travelClass, String quota,
                                      Integer daysBeforeJourney, Integer initialWlPosition, String finalStatus) {
        this.bookingHistId = bookingHistId;
        this.trainNo = trainNo;
        this.tripNumber = tripNumber;
        this.trainName = trainName;
        this.journeyDate = journeyDate;
        this.travelClass = travelClass;
        this.quota = quota;
        this.daysBeforeJourney = daysBeforeJourney;
        this.initialWlPosition = initialWlPosition;
        this.finalStatus = finalStatus;
    }

    public Long getBookingHistId() { return bookingHistId; }
    public void setBookingHistId(Long bookingHistId) { this.bookingHistId = bookingHistId; }

    public String getTrainNo() { return trainNo; }
    public void setTrainNo(String trainNo) { this.trainNo = trainNo; }

    public Integer getTripNumber() { return tripNumber; }
    public void setTripNumber(Integer tripNumber) { this.tripNumber = tripNumber; }

    public String getTrainName() { return trainName; }
    public void setTrainName(String trainName) { this.trainName = trainName; }

    public LocalDate getJourneyDate() { return journeyDate; }
    public void setJourneyDate(LocalDate journeyDate) { this.journeyDate = journeyDate; }

    public String getTravelClass() { return travelClass; }
    public void setTravelClass(String travelClass) { this.travelClass = travelClass; }

    public String getQuota() { return quota; }
    public void setQuota(String quota) { this.quota = quota; }

    public Integer getDaysBeforeJourney() { return daysBeforeJourney; }
    public void setDaysBeforeJourney(Integer daysBeforeJourney) { this.daysBeforeJourney = daysBeforeJourney; }

    public Integer getInitialWlPosition() { return initialWlPosition; }
    public void setInitialWlPosition(Integer initialWlPosition) { this.initialWlPosition = initialWlPosition; }

    public String getFinalStatus() { return finalStatus; }
    public void setFinalStatus(String finalStatus) { this.finalStatus = finalStatus; }

    // Builds a flat response from a HistoricalBooking entity - no nested Train object
    public static HistoricalBookingResponse fromEntity(com.railiq.entity.HistoricalBooking booking) {
        com.railiq.entity.Train train = booking.getTrain();

        return new HistoricalBookingResponse(
                booking.getBookingHistId(),
                train != null ? train.getId().getTrainNo() : null,
                train != null ? train.getId().getTripNumber() : null,
                train != null ? train.getTrainName() : null,
                booking.getJourneyDate(),
                booking.getTravelClass(),
                booking.getQuota(),
                booking.getDaysBeforeJourney(),
                booking.getInitialWlPosition(),
                booking.getFinalStatus()
        );
    }
}