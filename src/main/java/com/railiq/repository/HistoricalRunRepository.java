package com.railiq.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.railiq.entity.HistoricalRun;

@Repository
public interface HistoricalRunRepository extends JpaRepository<HistoricalRun, Long> {

    List<HistoricalRun> findByTrain_Id_TrainNoAndTrain_Id_TripNumber(String trainNo, Integer tripNumber);

    @Query("SELECT hr.train.id.trainNo, hr.train.id.tripNumber, hr.train.trainName, " +
           "AVG(hr.delayMinutes) as avgDelay, COUNT(hr) as totalRuns, " +
           "SUM(CASE WHEN hr.delayMinutes <= 15 THEN 1 ELSE 0 END) as onTimeRuns " +
           "FROM HistoricalRun hr " +
           "WHERE hr.train.sourceStation.stationCode = :source " +
           "AND hr.train.destStation.stationCode = :destination " +
           "GROUP BY hr.train.id.trainNo, hr.train.id.tripNumber, hr.train.trainName " +
           "ORDER BY avgDelay ASC")
    List<Object[]> findReliabilityByRoute(@Param("source") String source, @Param("destination") String destination);
}