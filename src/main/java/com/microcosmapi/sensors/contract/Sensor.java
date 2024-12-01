package sensors.contract;

import sensors.pojos.SensorData;

/*
 * Create a threadpool for each sensor
 */
public interface Sensor {
    
    /*
     * Contents contain pi4j configuration OOP
     */
    void initialize(); // override
    
    /*
     * override with actually reading data from sensor
     */

    SensorData readData(); 
    
    /*
     * Close the connection with sensor
     */
    void onShutdown();
}
