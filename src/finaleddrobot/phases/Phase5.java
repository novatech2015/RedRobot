/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

import finaleddrobot.FinalEDDRobot;
import finaleddrobot.actuators.StepperMotor;
import finaleddrobot.resources.Resources;

/**
 * Emergency Shutdown Phase
 * @author mallory
 */
public class Phase5 {

    private static boolean isInitialized = false;
    private static boolean hasRun = false;
    
    private static void setup() {
        
    }
    
    private static void loop(){
        System.out.println("In Phase 5");
        if(!Resources.m_hatch.isHatchOpen()){
            Resources.m_hatch.oneStep(StepperMotor.Direction.Forward);
        }else{
            System.exit(0);
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
        return hasRun;
    }
    
    
}
