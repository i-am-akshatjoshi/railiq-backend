package com.railiq.dto;

public class ConfirmationPredictionResponse {
    private double confirmationProbabilityPercent;

    public ConfirmationPredictionResponse() {
    }

    public double getConfirmationProbabilityPercent() { return confirmationProbabilityPercent; }
    public void setConfirmationProbabilityPercent(double confirmationProbabilityPercent) {
        this.confirmationProbabilityPercent = confirmationProbabilityPercent;
    }
}