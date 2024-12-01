package sockets.pojos;

import com.google.gson.Gson;
import java.util.List;

public class TemperatureData {
    private final List<String> timestamps;
    private final List<Double> temperatures;

    public TemperatureData(List<String> timestamps, List<Double> temperatures) {
        this.timestamps = timestamps;
        this.temperatures = temperatures;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }

    // Getters (optional, if needed elsewhere)
    public List<String> getTimestamps() {
        return timestamps;
    }

    public List<Double> getTemperatures() {
        return temperatures;
    }
}