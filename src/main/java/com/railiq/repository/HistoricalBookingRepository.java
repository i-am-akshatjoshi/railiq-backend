package com.railiq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.railiq.entity.HistoricalBooking;

@Repository
public interface HistoricalBookingRepository extends JpaRepository<HistoricalBooking, Long> {

    List<HistoricalBooking> findByTrain_Id_TrainNoAndTrain_Id_TripNumber(String trainNo, Integer tripNumber);
}