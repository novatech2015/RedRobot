/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot;

import finaleddrobot.resources.Resources;
import finaleddrobot.utility.StringFormatter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mallory
 */
public class MasterRobot {
    
    public static String grabAllData() throws IOException{
        //Read From All Sensors
        Resources.m_arduino.startDataMode();
        Resources.m_bmp180.read();
        Resources.m_tmp102.read();
        Resources.m_hih6130.read();
        
        //Store Values From All Sensors
        double temperature = Resources.m_tmp102.getTemp();
        Resources.m_bmp180.setTemperature(temperature);
        double humidity = Resources.m_hih6130.getHumidity();
        double pressure = Resources.m_bmp180.getPressure();
        double sampleTemp = -9001;
        try {
            sampleTemp = Resources.m_arduino.getData("S_TEMP");
        } catch (Exception ex) {
            System.out.println(ex);
            
        }
        
        //Send stop data signal to arduino
        Resources.m_arduino.stopDataMode();
        
        String data = "";
        try {
            //Format String
            data =  "TMP" + StringFormatter.prettify(temperature, 4) + "," +
                    "HIH" + StringFormatter.prettify(humidity, 4) + "," +
                    "BMP" + StringFormatter.prettify(pressure, 4) + "," +
                    "SMP" + StringFormatter.prettify(sampleTemp, 4) + ",";
        } catch (Exception ex) {
            Logger.getLogger(MasterRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        return data;
    }
    
    public static String grabMaxData() throws IOException{
        
        Resources.m_arduino.startDataMode();
        
        String data = "";
        while(data.length() < 720){
            //Read From All Sensors
            Resources.m_bmp180.read();
            Resources.m_tmp102.read();
            Resources.m_hih6130.read();

            //Store Values From All Sensors
            double temperature = Resources.m_tmp102.getTemp();
            Resources.m_bmp180.setTemperature(temperature);
            double humidity = Resources.m_hih6130.getHumidity();
            double pressure = Resources.m_bmp180.getPressure();
            double sampleTemp = -9001;
            try {
                sampleTemp = Resources.m_arduino.getData("S_TEMP");
            } catch (Exception ex) {
                System.out.println(ex);

            }

            //Send stop data signal to arduino
            Resources.m_arduino.stopDataMode();

            
            try {
                //Format String
                data +=  "TMP" + StringFormatter.prettify(temperature, 4) +
                         "HIH" + StringFormatter.prettify(humidity, 4) +
                         "BMP" + StringFormatter.prettify(pressure, 4) +
                         "SMP" + StringFormatter.prettify(sampleTemp, 4);
            } catch (Exception ex) {
                Logger.getLogger(MasterRobot.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data = data.substring(0,720);
        return data;
    }
    
}
