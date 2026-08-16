package com.railiq.repository;

import com.railiq.entity.Train;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Train.TrainId> {

    // A train number can now match up to 2 rows (one per direction)
    List<Train> findByIdTrainNo(String trainNo);

    // Use this when you need exactly one specific direction
    Optional<Train> findByIdTrainNoAndIdTripNumber(String trainNo, Integer tripNumber);
}