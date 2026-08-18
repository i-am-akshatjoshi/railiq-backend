package com.railiq.dto;

import java.util.List;

public class BatchConfirmationRequest {

    private List<ConfirmationPredictionRequest> trains;

    public List<ConfirmationPredictionRequest> getTrains() {
        return trains;
    }

    public void setTrains(List<ConfirmationPredictionRequest> trains) {
        this.trains = trains;
    }
}