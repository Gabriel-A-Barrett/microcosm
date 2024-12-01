package sensors.pojos;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class SensorData {
    private final String sensorName;
    private final LocalDateTime timestamp;
    private final Map<String, Object> data; // Store environmental variables dynamically

    public SensorData(String sensorName) {
        this.sensorName = sensorName;
        this.timestamp = LocalDateTime.now();
        this.data = new HashMap<>();
    }
    

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Map<String, Object> getData() {
        return data;
    }

    /**
     * Add a key-value pair to the sensor data.
     * 
     * @param key   the name of the variable (e.g., "temperature", "humidity").
     * @param value the value of the variable.
     */
    public void addData(String key, Object value) {
        data.put(key, value);
    }

    /**
     * Get the value of a specific variable by its key.
     * 
     * @param key the name of the variable.
     * @return the value of the variable, or null if it doesn't exist.
     */
    public Object getData(String key) {
        return data.get(key);
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "timestamp=" + timestamp +
                ", data=" + data +
                '}';
    }
}