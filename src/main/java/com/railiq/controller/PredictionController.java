package com.railiq.controller;

import com.railiq.dto.TrainReliability;
import com.railiq.dto.DelayPredictionRequest;
import com.railiq.dto.DelayPredictionResponse;
import com.railiq.dto.TrainRecommendation;
import com.railiq.dto.ConfirmationPredictionRequest;
import com.railiq.dto.ConfirmationPredictionResponse;
import com.railiq.entity.Train;
import com.railiq.repository.HistoricalRunRepository;
import com.railiq.repository.TrainRepository;
import com.railiq.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@RestController
@RequestMapping("/api/predictions")
public class PredictionController {

    private static final String ML_SERVICE_BASE = "http://localhost:8000";

    @Autowired
    private HistoricalRunRepository historicalRunRepository;

    @Autowired
    private TrainRepository trainRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private WeatherService weatherService;

    // GET /api/predictions/best-trains?source=HYB&destination=AII
    @GetMapping("/best-trains")
    public List<TrainReliability> getBestTrains(
            @RequestParam String source,
            @RequestParam String destination) {
        List<Object[]> rows = historicalRunRepository.findReliabilityByRoute(source, destination);
        List<TrainReliability> results = new ArrayList<>();
        for (Object[] row : rows) {
            String trainNo = (String) row[0];
            Integer tripNumber = (Integer) row[1];
            String trainName = (String) row[2];
            Double avgDelay = (Double) row[3];
            Long totalRuns = (Long) row[4];
            Long onTimeRuns = (Long) row[5];
            Double onTimePct = totalRuns > 0 ? (onTimeRuns * 100.0 / totalRuns) : 0.0;
            results.add(new TrainReliability(trainNo, tripNumber, trainName, avgDelay, totalRuns, onTimeRuns, onTimePct));
        }
        return results;
    }

    // GET /api/predictions/delay-forecast/{trainNo}/{tripNumber}?date=2026-09-15
    @GetMapping("/delay-forecast/{trainNo}/{tripNumber}")
    public ResponseEntity<?> getDelayForecast(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber,
            @RequestParam(required = false) String date) {
        Optional<Train> trainOpt = trainRepository.findByIdTrainNoAndIdTripNumber(trainNo, tripNumber);
        if (trainOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Train not found");
        }
        Train train = trainOpt.get();
        LocalDate journeyDate = (date != null) ? LocalDate.parse(date) : LocalDate.now();
        String dayOfWeek = journeyDate.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH).toUpperCase();

        double weatherRisk = weatherService.getWeatherRiskScore(train.getSourceStation().getStationName());

        DelayPredictionRequest mlRequest = new DelayPredictionRequest();
        mlRequest.setDistanceKm(train.getDistanceKm());
        mlRequest.setDurationMinutes(train.getDurationMinutes());
        mlRequest.setTrainType(train.getTrainType() != null ? train.getTrainType() : "Pass");
        mlRequest.setDayOfWeek(dayOfWeek);
        mlRequest.setIsFestivalSeason(0);
        mlRequest.setWeatherRiskScore(weatherRisk);

        DelayPredictionResponse mlResponse = restTemplate.postForObject(
                ML_SERVICE_BASE + "/predict-delay",
                mlRequest,
                DelayPredictionResponse.class
        );
        return ResponseEntity.ok(mlResponse);
    }

    // GET /api/predictions/confirmation-probability/{trainNo}/{tripNumber}
    //     ?daysBeforeJourney=10&initialWlPosition=23&quota=GN&travelClass=SL
    @GetMapping("/confirmation-probability/{trainNo}/{tripNumber}")
    public ResponseEntity<?> getConfirmationProbability(
            @PathVariable String trainNo,
            @PathVariable Integer tripNumber,
            @RequestParam int daysBeforeJourney,
            @RequestParam int initialWlPosition,
            @RequestParam String quota,
            @RequestParam String travelClass) {

        Optional<Train> trainOpt = trainRepository.findByIdTrainNoAndIdTripNumber(trainNo, tripNumber);
        if (trainOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Train not found");
        }

        double clearanceRate = getTrainClearanceRate(trainNo, tripNumber);

        ConfirmationPredictionRequest mlRequest = new ConfirmationPredictionRequest();
        mlRequest.setDaysBeforeJourney(daysBeforeJourney);
        mlRequest.setInitialWlPosition(initialWlPosition);
        mlRequest.setQuota(quota);
        mlRequest.setTravelClass(travelClass);
        mlRequest.setTrainClearanceRate(clearanceRate);

        ConfirmationPredictionResponse mlResponse = restTemplate.postForObject(
                ML_SERVICE_BASE + "/predict-confirmation",
                mlRequest,
                ConfirmationPredictionResponse.class
        );
        return ResponseEntity.ok(mlResponse);
    }

    // Computes this train's historical clearance rate from HISTORICAL_BOOKINGS_V2.
    // Falls back to 0.64 (the dataset-wide average) if this specific train has no rows yet.
    // NOTE: uses COALESCE (Postgres) instead of NVL (Oracle-only), and no FROM DUAL
    // (Postgres doesn't need or support that).
    private double getTrainClearanceRate(String trainNo, Integer tripNumber) {
        String sql = "SELECT COALESCE(" +
                "  (SELECT SUM(CASE WHEN FINAL_STATUS = 'CONFIRMED' THEN 1 ELSE 0 END) * 1.0 / COUNT(*) " +
                "   FROM HISTORICAL_BOOKINGS_V2 " +
                "   WHERE TRAIN_NO = ? AND TRIP_NUMBER = ?), " +
                "  0.64)";
        Double rate = jdbcTemplate.queryForObject(sql, Double.class, trainNo, tripNumber);
        return rate != null ? rate : 0.64;
    }

    // GET /api/predictions/recommend?source=HYB&destination=AII
    //  &daysBeforeJourney=10&initialWlPosition=23&quota=GN&travelClass=SL
    @GetMapping("/recommend")
    public List<TrainRecommendation> getRecommendations(
         @RequestParam String source,
         @RequestParam String destination,
         @RequestParam int daysBeforeJourney,
         @RequestParam int initialWlPosition,
         @RequestParam String quota,
         @RequestParam String travelClass) {

        List<Object[]> rows = historicalRunRepository.findReliabilityByRoute(source, destination);
        List<TrainRecommendation> results = new ArrayList<>();

        for (Object[] row : rows) {
            String trainNo = (String) row[0];
            Integer tripNumber = (Integer) row[1];
            String trainName = (String) row[2];
            Double avgDelay = (Double) row[3];
            Long totalRuns = (Long) row[4];
            Long onTimeRuns = (Long) row[5];
            Double onTimePct = totalRuns > 0 ? (onTimeRuns * 100.0 / totalRuns) : 0.0;

            double clearanceRate = getTrainClearanceRate(trainNo, tripNumber);

            ConfirmationPredictionRequest mlRequest = new ConfirmationPredictionRequest();
            mlRequest.setDaysBeforeJourney(daysBeforeJourney);
            mlRequest.setInitialWlPosition(initialWlPosition);
            mlRequest.setQuota(quota);
            mlRequest.setTravelClass(travelClass);
            mlRequest.setTrainClearanceRate(clearanceRate);

            ConfirmationPredictionResponse confResponse = restTemplate.postForObject(
                    ML_SERVICE_BASE + "/predict-confirmation",
                    mlRequest,
                    ConfirmationPredictionResponse.class
            );

            Double confirmationPct = confResponse != null ? confResponse.getConfirmationProbabilityPercent() : 0.0;

            // 60% weight on confirmation probability, 40% on reliability
            double combinedScore = (0.6 * confirmationPct) + (0.4 * onTimePct);

            results.add(new TrainRecommendation(
                    trainNo, tripNumber, trainName,
                    onTimePct, avgDelay, confirmationPct,
                    Math.round(combinedScore * 10.0) / 10.0
            ));
        }

        results.sort((a, b) -> Double.compare(b.getCombinedScore(), a.getCombinedScore()));
        return results;
    }
}