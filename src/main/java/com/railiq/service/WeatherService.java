package com.railiq.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.regex.Pattern;

@Service
public class WeatherService {

    @Value("${weather.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private static final Pattern STATION_SUFFIXES = Pattern.compile(
        "\\b(JN|JUNCTION|TERM|TERMINUS|CENTRAL|CANTT|ROAD|CITY)\\b",
        Pattern.CASE_INSENSITIVE
    );

    /**
     * Returns a 0-10 delay-risk score based on real current weather at the given
     * station. Falls back to 5.0 (neutral) if the station name can't be resolved
     * or the API call fails for any reason - predictions should never break just
     * because weather data is temporarily unavailable.
     */
    public double getWeatherRiskScore(String stationName) {
        if (stationName == null || stationName.isBlank()) {
            return 5.0;
        }

        String cityQuery = cleanStationName(stationName);

        try {
            String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?q=%s,IN&appid=%s&units=metric",
                cityQuery, apiKey
            );

            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null) {
                return 5.0;
            }

            return computeRiskScore(response);

        } catch (RestClientException e) {
            // City not found, API down, rate limit hit, etc. - don't fail the
            // whole prediction over a weather lookup, just use the neutral default.
            return 5.0;
        }
    }

    private String cleanStationName(String stationName) {
        String cleaned = STATION_SUFFIXES.matcher(stationName).replaceAll("");
        cleaned = cleaned.trim().replaceAll("\\s+", " ");
        return cleaned.isEmpty() ? stationName.trim() : cleaned;
    }

    @SuppressWarnings("unchecked")
    private double computeRiskScore(Map<String, Object> weatherData) {
        double risk = 2.0; // baseline: clear conditions

        try {
            var weatherList = (java.util.List<Map<String, Object>>) weatherData.get("weather");
            if (weatherList != null && !weatherList.isEmpty()) {
                String main = (String) weatherList.get(0).get("main");
                risk = switch (main) {
                    case "Thunderstorm" -> 9.0;
                    case "Rain", "Drizzle" -> 7.0;
                    case "Fog", "Mist", "Haze" -> 6.5;
                    case "Snow" -> 8.0;
                    case "Clouds" -> 3.5;
                    case "Clear" -> 2.0;
                    default -> 5.0;
                };
            }

            Map<String, Object> main = (Map<String, Object>) weatherData.get("main");
            if (main != null && main.get("temp") != null) {
                double temp = ((Number) main.get("temp")).doubleValue();
                // Extreme heat is a real, documented cause of Indian Railways
                // speed restrictions (heat-related rail buckling risk)
                if (temp > 45) {
                    risk = Math.max(risk, 7.0);
                }
            }

            Integer visibility = (Integer) weatherData.get("visibility");
            if (visibility != null && visibility < 1000) {
                // Under 1km visibility - significant fog/haze risk
                risk = Math.max(risk, 7.5);
            }

        } catch (Exception e) {
            return 5.0;
        }

        return Math.min(10.0, risk);
    }
}