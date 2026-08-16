package com.railiq.dto;

public class PnrStatusResponse {
    private String pnr;
    private String currentStatus;

    public PnrStatusResponse(String pnr, String currentStatus) {
        this.pnr = pnr;
        this.currentStatus = currentStatus;
    }

    public String getPnr() {
        return pnr;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }
}
