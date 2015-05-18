/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.phases;

/**
 * Termination Phase
 * @author mallory
 */
public class Phase6 {

    private static boolean isInitialized = false;
    private static boolean hasRun = false;
    
    private static void setup() {
        
    }
    
    private static void loop(){
        System.exit(0);
        hasRun = true;
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
