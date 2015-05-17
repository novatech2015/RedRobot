package finaleddrobot;

import finaleddrobot.phases.*;

public class FinalEDDRobot{

    private static byte autophase = 0;	

    public static void main(String[] args){

        while(true){
            if(autophase == 0){
                    Phase0.update();
                    if(Phase0.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 1){
                    Phase1.update();
                    if(Phase1.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 2){
                    Phase2.update();
                    if(Phase2.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 3){
                    Phase3.update();
                    if(Phase3.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 4){
                    Phase4.update();
                    if(Phase4.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 5){
                    Phase5.update();
                    if(Phase5.hitFlag()){
                        autophase++;
                    }
            }else if(autophase == 6){
                    Phase6.update();
                    if(Phase6.hitFlag()){
                        autophase++;
                    }
            }
        }
    }
}