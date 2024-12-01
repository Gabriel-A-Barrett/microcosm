package sockets.http;

import sensors.BME280Sensor;

import sockets.pojos.TemperatureData;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TemperatureHandler {
    public String getTemperatureData() {

        //BME280Sensor bme280 = new BME280Sensor();

        List<String> timestamps = new ArrayList<>();
        List<Double> temperatures = new ArrayList<>();

        LocalDateTime now = LocalDateTime.now();
        for (int i = 60; i >= 0; i -= 5) { // Generate readings every 5 minutes for the last hour
            timestamps.add(now.minusMinutes(i).toString());
            temperatures.add(20 + Math.random() * 5); // Simulate temperatures between 20°C and 25°C
        }

        TemperatureData temperatureData = new TemperatureData(timestamps, temperatures);
        return temperatureData.toJson(); // Convert to JSON
    }
}