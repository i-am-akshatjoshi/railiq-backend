package com.railiq.dto;

public class ConfirmationPredictionRequest {
    private int daysBeforeJourney;
    private int initialWlPosition;
    private String quota;
    private String travelClass;
    private double trainClearanceRate;

    public ConfirmationPredictionRequest() {
    }

    public int getDaysBeforeJourney() { return daysBeforeJourney; }
    public void setDaysBeforeJourney(int daysBeforeJourney) { this.daysBeforeJourney = daysBeforeJourney; }

    public int getInitialWlPosition() { return initialWlPosition; }
    public void setInitialWlPosition(int initialWlPosition) { this.initialWlPosition = initialWlPosition; }

    public String getQuota() { return quota; }
    public void setQuota(String quota) { this.quota = quota; }

    public String getTravelClass() { return travelClass; }
    public void setTravelClass(String travelClass) { this.travelClass = travelClass; }

    public double getTrainClearanceRate() { return trainClearanceRate; }
    public void setTrainClearanceRate(double trainClearanceRate) { this.trainClearanceRate = trainClearanceRate; }
}