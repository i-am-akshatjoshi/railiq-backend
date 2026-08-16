package com.railiq.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "PREDICTIONS_LOG")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class PredictionLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PREDICTION_ID")
    private Long predictionId;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "TRAIN_NO", referencedColumnName = "TRAIN_NO"),
        @JoinColumn(name = "TRIP_NUMBER", referencedColumnName = "TRIP_NUMBER")
    })
    private Train train;

    @Column(name = "PREDICTION_TYPE", nullable = false, length = 30)
    private String predictionType;

    @Lob
    @Column(name = "REQUEST_PAYLOAD")
    private String requestPayload;

    @Lob
    @Column(name = "RESPONSE_PAYLOAD")
    private String responsePayload;

    @Column(name = "PREDICTED_VALUE")
    private Double predictedValue;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}