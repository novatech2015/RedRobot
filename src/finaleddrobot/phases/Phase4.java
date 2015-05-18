/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

import finaleddrobot.FinalEDDRobot;
import finaleddrobot.MasterRobot;
import finaleddrobot.resources.Resources;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Collection Phase
 * @author mallory
 */
public class Phase4 {

    private static boolean isInitialized = false;
    private static boolean isDataStored = false;
    
    private static void setup() {
        
    }
    
    private static void loop(){
        try {
            String data = MasterRobot.grabMaxData();
            Resources.m_mifareStringBuilder.appendString(data);
            Resources.m_pn532.write(Resources.m_mifareStringBuilder.getMifareString());
            Resources.m_arduino.syncState(FinalEDDRobot.autophase);
            isDataStored = true;
        } catch (IOException ex) {
            Logger.getLogger(Phase4.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Phase4.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void update() {
        if(!isInitialized){
            setup();
            isInitialized = true;
        }else{
            loop();
        }
    }

    public static boolean hitFlag() {
        return isDataStored;
    }
    
    
}
