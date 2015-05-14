package finaleddrobot;

import finaleddrobot.sensors.PN532;
import finaleddrobot.utility.MiFareStringBuilder;
import java.io.IOException;

public class FinalEDDRobot{
	
	private static byte autophase = 0;	

        
        
	public static void main(String[] args){
            try{
                PN532 pn532 = new PN532();
                MiFareStringBuilder mfstringBuilder = new MiFareStringBuilder();
                pn532.write(mfstringBuilder.getMifareString());
            }catch(Exception e){
                System.out.println(e);
            }
            
            
            //while(true){
                    if(autophase == 0){
                            phase0(); 
                    }else if(autophase == 1){
                            phase1(); 
                    }else if(autophase == 2){
                            phase2();
                    }else if(autophase == 3){
                            phase3();
                    }else if(autophase == 4){
                            phase4();
                    }else if(autophase == 5){
                            phase5();
                    }else if(autophase == 6){
                            phase6();
                    }
            //}
	}

	public static void phase0(){

	}

	public static void phase1(){

	}

	public static void phase2(){

	}

	public static void phase3(){

	}

	public static void phase4(){

	}

	public static void phase5(){

	}

	public static void phase6(){

	}
        
        public static boolean syncPhase(){
            return false;
        }


}