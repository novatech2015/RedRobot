package finaleddrobot;

import finaleddrobot.phases.*;
import finaleddrobot.resources.Resources;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FinalEDDRobot{

    public static byte autophase = 0;	

    public static void main(String[] args) {

        try {
            Resources.init();
        } catch (IOException ex) {
            Logger.getLogger(FinalEDDRobot.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true){
            //Start Phase
            if(autophase == 0){
                System.out.println("HIT PHASE 0");
                Phase0.update();
                if(Phase0.hitFlag()){
                    autophase++;
                }
            //Autonomous Initialization Phase
            }else if(autophase == 1){
                System.out.println("HIT PHASE 1");
                Phase1.update();
                if(Phase1.hitFlag()){
                    autophase++;
                }
            //Tunnel Creation Phase
            }else if(autophase == 2){
                System.out.println("HIT PHASE 2");
                Phase2.update();
                if(Phase2.hitFlag()){
                    autophase++;
                }      
            //Sample Location Phase
            }else if(autophase == 3){
                System.out.println("HIT PHASE 3");
                Phase3.update();
                if(Phase3.hitFlag()){
                    autophase++;
                }
            //Data Collection Phase
            }else if(autophase == 4){
                System.out.println("HIT PHASE 4");
                Phase4.update();
                if(Phase4.hitFlag()){
                    autophase++;
                }
            //Emergency Shutdown Phase
            }else if(autophase == 5){
                System.out.println("HIT PHASE 5");
                Phase5.update();
                if(Phase5.hitFlag()){
                    autophase++;
                }
            //Termination Phase
            }else if(autophase == 6){
                System.out.println("HIT PHASE 6");
                Phase6.update();
                if(Phase6.hitFlag()){
                    autophase++;
                }
            }
        }
    }
}