/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.sensors;

import finaleddrobot.utility.SerialDevice;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Note: This assumes that the Raspberry Pi is the master and
 * the Arduino is the slave.
 * @author mallory
 */
public class Arduino {
    
    SerialDevice m_arduino;
    boolean dataPhase = false;
    int stateIncrement = 0;
    String recentData = "";
    
    
    public Arduino() throws IOException{
        m_arduino = new SerialDevice("/dev/ttyUSB0");
    }
    
    public Arduino(String address) throws IOException{
        m_arduino = new SerialDevice(address);
    }
    
    public void syncState(int state) throws IOException, Exception{
        if(dataPhase){
            throw new Exception("SERIAL LINE BUSY!!!! Shut down data phase!");
        }
        stateIncrement = state;
        m_arduino.write("$STATE " + state + ",");
        stateIncrement++;
        while(m_arduino.read().length == 0){
            Thread.sleep(10);
        }
    }
    
    public void syncState() throws Exception{
        if(dataPhase){
            throw new Exception("SERIAL LINE BUSY!!!! Shut down data phase!");
        }
        m_arduino.write("$STATE " + stateIncrement + ",");
        stateIncrement++;
        while(m_arduino.read().length == 0){
            Thread.sleep(10);
        }
    }
    
    public boolean startDataMode() throws IOException{
        dataPhase = true;
        m_arduino.write("-2");
        return true;
    }
    
    public boolean stopDataMode() throws IOException{
        dataPhase = false;
        m_arduino.write("-1");
        return false;
    }
    
    public String getIncoming() throws IOException{
        if(dataPhase){
            
        }else{
            
        }
        return new String(m_arduino.read());
    }
    
    public double getData(String searchTerm){
        String regex = "[^$" + searchTerm + " ]";
        
        Pattern pattern = Pattern.compile(regex);
        
        Matcher matcher = pattern.matcher(testString);
        return 4.0;
    }
    
}
