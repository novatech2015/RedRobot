/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

import finaleddrobot.resources.Resources;

/**
 * Sample Location Phase
 * @author mallory
 */
public class Phase3 {

    private static boolean isInitialized = false;
    private static int localState = -9001;
    
    private static void setup() {
        
    }
    
    private static void loop(){
        System.out.println("In Phase 3");
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
        return (localState == 4);
    }
    
    
}
