/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

import finaleddrobot.resources.Resources;
import finaleddrobot.resources.RobotConstants;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tunnel Creation Phase
 * @author mallory
 */
public class Phase2 {

    private static boolean isInitialized = false;
    private static boolean drillPhase = false;
    private static int drillInt = 0;
    private static int localState = -9001;
    
    private static void setup() {
        
    }
    
    private static void loop() throws IOException{
        System.out.println("In Phase 2");
        
        Resources.m_arduino.startDataMode();
        try {
            drillInt = (int) Resources.m_arduino.getData("DRILL");
        } catch (Exception ex) {
            drillInt = 0;
        }
        Resources.m_arduino.stopDataMode();
        
        if(drillInt != 0){
            Resources.m_drill.cycleLeft(RobotConstants.drillAngle);
            try {
                Thread.sleep(RobotConstants.drillSleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Phase2.class.getName()).log(Level.SEVERE, null, ex);
            }
            Resources.m_drill.cycleRight(2*RobotConstants.drillAngle);
            try {
                Thread.sleep(RobotConstants.drillSleepTime);
            } catch (InterruptedException ex) {
                Logger.getLogger(Phase2.class.getName()).log(Level.SEVERE, null, ex);
            }
            Resources.m_drill.cycleLeft(RobotConstants.drillAngle);
        }
        try {
            localState = Resources.m_arduino.slaveSyncState();
        } catch (Exception ex) {
            localState = -9001;
        }
    }

    public static void update() throws IOException {
        if(!isInitialized){
            setup();
            isInitialized = true;
        }else{
            loop();
        }
    }

    public static boolean hitFlag() {
        return (localState == 3);
    }
    
    
}
