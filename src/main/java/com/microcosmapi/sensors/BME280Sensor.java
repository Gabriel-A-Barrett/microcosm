package sensors;

import sensors.contract.Sensor; // interface gets overrided
import sensors.pojos.SensorData; // OOP

import com.pi4j.Pi4J;
import com.pi4j.io.i2c.I2C;
import com.pi4j.io.i2c.I2CConfig;
import com.pi4j.io.i2c.I2CProvider;

public class BME280Sensor implements Sensor {
    private static final int BME280_ADDRESS = 0x76; // Default I2C address
    private static final int I2C_BUS = 0x01;

    private I2CProvider i2cProvider;
    private I2CConfig config;
    private I2C bme280;

    @Override
    public void initialize() {

        var pi4j = Pi4J.newAutoContext();
        //TODO: check if sensor is found on I2C bus

        I2CProvider i2cProvider = pi4j.provider("linuxfs-i2c");

        I2CConfig config = I2C.newConfigBuilder(pi4j)
                .id("BME280")
                .bus(I2C_BUS) // I2C bus 1
                .device(BME280_ADDRESS)
                .build();

        System.out.println("BME280 Sensor Initialized.");
    }

    @Override
    public SensorData readData() {
        SensorData sensorData = new SensorData("BME280"); // OOP checks if found on i2c

        try (I2C bme280 = i2cProvider.create(config)) {
            
            // BME example resource:
                // https://github.com/Pi4J/pi4j-example-devices/blob/master/src/main/java/com/pi4j/devices/bme280/BME280DeviceI2C.java

            //resetSensor(bme280);

            //Thread.sleep(300);

            //getMeasurments(bme280);

            //Thread.sleep(5000);
            
            // Replace with actual BME280 I2C register reads
            double temperature = readTemperature();
            double humidity = readHumidity();
            double pressure = readPressure();

            sensorData.addData("temperature", temperature);
            sensorData.addData("humidity", humidity);
            sensorData.addData("pressure", pressure);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sensorData;
    }

    @Override
    public void onShutdown() {
        try {
            if (bme280 != null) {
                bme280.close();
                System.out.println("BME280 sensor connection closed.");
            }
        } catch (Exception e) {
            System.err.println("Failed to close BME280 sensor connection: " + e.getMessage());
        }
    }

    /*private static void resetSensor(I2C device) throws Exception {
        
        int rc = device.writeRegister(BMP280Declares.reset);
    }*/

    private double readTemperature() {
        return 22.5; // Simulated temperature value
    }

    private double readHumidity() {
        return 55.2; // Simulated humidity value
    }

    private double readPressure() {
        return 1013.25; // Simulated pressure value
    }
}