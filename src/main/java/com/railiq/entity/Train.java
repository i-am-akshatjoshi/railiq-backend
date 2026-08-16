package com.railiq.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "TRAINS")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Train {

    @EmbeddedId
    private TrainId id;

    @Column(name = "TRAIN_NAME", nullable = false, length = 150)
    private String trainName;

    @ManyToOne
    @JoinColumn(name = "SOURCE_STATION", referencedColumnName = "STATION_CODE", nullable = false)
    private Station sourceStation;

    @ManyToOne
    @JoinColumn(name = "DEST_STATION", referencedColumnName = "STATION_CODE", nullable = false)
    private Station destStation;

    @Column(name = "TRAIN_TYPE", length = 30)
    private String trainType;

    @Column(name = "CLASSES_AVAILABLE", length = 50)
    private String classesAvailable;

    @Column(name = "DISTANCE_KM")
    private Double distanceKm;

    @Column(name = "DURATION_MINUTES")
    private Integer durationMinutes;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Embeddable
    @Getter @Setter @NoArgsConstructor @AllArgsConstructor
    public static class TrainId implements Serializable {

        @Column(name = "TRAIN_NO", length = 15)
        private String trainNo;

        @Column(name = "TRIP_NUMBER")
        private Integer tripNumber;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TrainId)) return false;
            TrainId that = (TrainId) o;
            return Objects.equals(trainNo, that.trainNo)
                && Objects.equals(tripNumber, that.tripNumber);
        }

        @Override
        public int hashCode() {
            return Objects.hash(trainNo, tripNumber);
        }
    }
}