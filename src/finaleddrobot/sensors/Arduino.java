package finaleddrobot.sensors;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
        m_arduino.write("$STATE " + state + " ,");
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
        m_arduino.write("" + -2);
        return true;
    }
    
    public boolean stopDataMode() throws IOException{
        dataPhase = false;
        m_arduino.write("-1");
        return false;
    }
    
    public String getIncoming() throws IOException, Exception{
        if(dataPhase){
            char[] cbuf = new char[512];
            recentData = new String(m_arduino.read());
        }else{
            throw new Exception("DATA PHASE IS OFF!!!! Turn on data phase!");
        }
        return recentData;
    }
    
    public double getData(String searchTerm) throws Exception{
        String testString = this.getIncoming();
        //Split by New Line
        String[] line = testString.split("\n");
        //Inner part of regex which is to be reused
        String regexWord = "$" + searchTerm + " ,";
        //First Regex used to find name qualifiers
        String regex = "[" + regexWord + " ,]+";
        
        //Object construction
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher;
        String resultString = "";
        
        //Finds Matches of name qualifier
        int[] indices = new int[2];
        for(int i = 0; i < line.length; i++){
            matcher = pattern.matcher(line[i]);
            if(matcher.find()){
                if(matcher.group().equals(regexWord)){
                    indices[0] = indices[1];
                    indices[1] = i;
                }
            }
        }
        
        
        //Finds corresponding numbers if available
        pattern = Pattern.compile("[\\d\\.]+");
        matcher = pattern.matcher(line[indices[1]]);
        if(matcher.find()){
        }else{
            matcher = pattern.matcher(line[indices[0]]);
            matcher.find();
        }
        try{
            resultString = matcher.group();
            System.out.println("MATCH");
        }catch(Exception e){
            System.out.println("NO MATCH");
        }
        
        //Alert if no match
        if(resultString.equals("")){
            throw new Exception("No Match!!!");
        }
        
        //Return resulting number as double
        double result = Double.parseDouble(resultString);
        return result;
    }
    
}
