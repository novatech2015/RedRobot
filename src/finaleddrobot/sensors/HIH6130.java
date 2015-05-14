package finaleddrobot.sensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * I2C Hygrometer.
 * @author Mr. Mallory
 */
public class HIH6130 {
    
    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_humiditySensor;//Declares the I2CDevice
    private double m_humidity = 0.0;
    private double m_temperature = 0.0;
    
    /**
     * Constructor.
     */
    public HIH6130(){
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");
            
            m_humiditySensor = bus.getDevice(0x27);//Gets device on the bus 
            System.out.println("HIH6130 Connected");
	} catch (IOException e) {
            System.out.println(e);
	}
    }
    
    /**
     * Updates the registers on the I2C Hygrometer.
     */   
    public void read(){
        try {
            byte[] buffer = new byte[4];//Declares a buffer which will hold the ADXL345 data
	    Thread.sleep(100);
	    m_humiditySensor.read(0x27, buffer, 0, 4);//Reads data into the buffer
            
	    //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            m_temperature = (((buffer[2] << 8) & 0xff00) | (buffer[3] & 0xff))*0.01007141549/4 - 40.0;
            m_humidity = (((buffer[0] << 8) & 0x3f00) | (buffer[1] & 0xff)) * 6.10e-3;
	} catch (IOException e){
            System.out.println(e);
	} catch (InterruptedException ex) {
            Logger.getLogger(HIH6130.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns the most current value from the hygrometer's humidity register since the read() function was called.
     * @return The most current value from the hygrometer's humidity register.
     */
    public double getHumidity(){
        return m_humidity;
    }
    
    /**
     * Returns the most current value from the hygrometer's temperature register since the read() function was called.
     * @return The most current value from the hygrometer's temperature register.
     */
    public double getTemperature(){
        return m_temperature;
    }
}