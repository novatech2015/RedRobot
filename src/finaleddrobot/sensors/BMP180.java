package finaleddrobot.sensors;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * I2C Barometer.
 * @author Mr. Mallory
 */
public class BMP180 {

    private I2CBus bus;//Declares the I2CBus
    private I2CDevice m_pressureSensor;//Declares the I2CDevice
    private double m_pressure = 0;
    private double m_temperature = 0;
    private int AC1, AC2, AC3, B1, B2, MB, MC, MD;
    private long AC4, AC5, AC6;
    private long X1, X2, X3, B3, B5, B6;
    private double c3, c4, b1, c5, c6, mc, md, x0, x1, x2, y0, y1, y2, p0, p1, p2;
    private long B4, B7;

    /**
     * Constructor.
     */
    public BMP180() {
        try {
            bus = I2CFactory.getInstance(I2CBus.BUS_1);//Connects the bus
            System.out.println("Bus Connected");

            m_pressureSensor = bus.getDevice(0x77);//Gets device on the bus 
            System.out.println("BMP180 Connected");

            AC1 = 6452;
            AC2 = -1135;
            AC3 = -14383;
            AC4 = 33116;
            AC5 = 25600;
            AC6 = 16333;
            B1 = 6515;
            B2 = 43;
            MB = -32768;
            MC = -11786;
            MD = 2454;

            c3 = 160.0 * Math.pow(2, -15) * AC3;
            c4 = Math.pow(10, -3) * Math.pow(2, -15) * AC4;
            b1 = Math.pow(160, 2) * Math.pow(2, -30) * B1;
            c5 = (Math.pow(2, -15) / 160) * AC5;
            c6 = AC6;
            mc = (Math.pow(2, 11) / Math.pow(160, 2)) * MC;
            md = MD / 160.0;
            x0 = AC1;
            x1 = 160.0 * Math.pow(2, -13) * AC2;
            x2 = Math.pow(160, 2) * Math.pow(2, -25) * B2;
            y0 = c4 * Math.pow(2, 15);
            y1 = c4 * c3;
            y2 = c4 * b1;
            p0 = (3791.0 - 8.0) / 1600.0;
            p1 = 1.0 - 7357.0 * Math.pow(2, -20);
            p2 = 3038.0 * 100.0 * Math.pow(2, -36);
            m_pressureSensor.write(0xF4, (byte) 0x2E);//Power on Calibrate
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Updates the registers on the I2C Barometer.
     */
    public void read() {
        readTemperature();
        readPressure();
    }

    /**
     * Returns the most current value from the barometer's pressure register since the read() function was called.
     * @return The most current value from the barometer's pressure register.
     */
    public double getPressure() {
        return m_pressure;
    }

    public double getTemperature() {
        return m_temperature;
    }

    /**
     * Updates the temperature register on the I2C Barometer.
     */
    private void readTemperature() {
        try {
            m_pressureSensor.write(0xF4, (byte) 0x2E);//Configure Data Register
            Thread.sleep(5);
            byte[] buffer = new byte[2];//Declares a buffer which will hold the BMP180 data
            m_pressureSensor.read(0xF6, buffer, 0, 2);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            double uncompensatedTemperature = buffer[0] * 256.0 + buffer[1];
            double a = c5 * (uncompensatedTemperature - c6);
            m_temperature = a + (mc / (a + md));
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(BMP180.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the pressure register on the I2C Barometer.
     */
    private void readPressure() {
        try {
            m_pressureSensor.write(0xF4, (byte) 0xF4);//Configure Data Register
            Thread.sleep(26);
            byte[] buffer = new byte[3];//Declares a buffer which will hold the BMP180 data
            m_pressureSensor.read(0xF6, buffer, 0, 3);//Reads data into the buffer
            //Note : Pi is little endian?
            //Converts the buffer to usable data
            //Even Numbers represent LSB and Odd Numbers represent MSB
            double s, x, y, z;
            double uncompensatedPressure = (toShort(buffer[0])*256.0) + toShort(buffer[1]) + (toShort(buffer[2])/256.0);
	    System.out.println("UP = " + uncompensatedPressure);
            s = m_temperature - 25.0;
            x = (x2 * Math.pow(s,2)) + (x1 * s) + x0;
            y = (y2 * Math.pow(s,2)) + (y1 * s) + y0;
            z = (uncompensatedPressure - x) / y;
            m_pressure = (p2 * Math.pow(z,2)) + (p1 * z) + p0;
        } catch (IOException e) {
            System.out.println(e);
        } catch (InterruptedException ex) {
            Logger.getLogger(BMP180.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Sets the barometer's temperature for reference when calibrating the pressure.
     * @param temperature The temperature read from another source.
     */
    public void setTemperature(double temperature) {
        m_temperature = temperature;
    }

    private int toShort(byte value){
	int val = 0;
	for(int i = 0; i < 8; i++){
		if((value & (int) Math.pow(2,i)) > 0){
			val += (1 << i);
			System.out.println("+1");
		}
	}
	System.out.println("Val = " + val);
	return val;
    }
}