package com.railiq.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
public class BookingRequest {
    private String trainNo;
    private Integer tripNumber;
    private LocalDate journeyDate;
    private String travelClass;
    private String quota;
}