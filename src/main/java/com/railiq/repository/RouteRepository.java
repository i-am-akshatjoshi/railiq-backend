package com.railiq.repository;

import com.railiq.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {

    List<Route> findByTrain_Id_TrainNoAndTrain_Id_TripNumberOrderByStopSequenceAsc(
            String trainNo, Integer tripNumber);
}