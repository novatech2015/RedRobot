/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

import finaleddrobot.resources.Resources;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Autonomous Initialization Phase
 * @author mallory
 */
public class Phase1 {

    private static boolean isInitialized = false;
    private static boolean hasSynced = false;
    private static int localState = -9001;
    
    private static void setup() {
        
    }
    
    private static void loop(){
        System.out.println("In Phase 1");
        try {
            localState = Resources.m_arduino.slaveSyncState();
        } catch (Exception ex) {
            localState = -9001;
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
        return (localState == 2);
    }
    
    
}
