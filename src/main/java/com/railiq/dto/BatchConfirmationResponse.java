package com.railiq.dto;

import java.util.List;

public class BatchConfirmationResponse {

    private List<Double> confirmationProbabilities;

    public List<Double> getConfirmationProbabilities() {
        return confirmationProbabilities;
    }

    public void setConfirmationProbabilities(List<Double> confirmationProbabilities) {
        this.confirmationProbabilities = confirmationProbabilities;
    }
}