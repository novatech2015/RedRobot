/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.utility;

/**
 *
 * @author mallory
 */
public class StringFormatter {
    
    public static String prettify(double value, int sigFigs) throws Exception{
        if(sigFigs < 0){
            throw new Exception("Significant Figures can't be a negative number");
        }else if(sigFigs == 0){
            throw new Exception("Significant Figures can't be zero");
        }
        double originalValue = value;
        String prettyString = "";
        String substring = Long.toString((long) value);
        int length = substring.length();
        double scaleFactor = Math.pow(10.0, sigFigs-1);
        long tempVal = (long) (Math.round(value * scaleFactor)*10.0);
        substring = Long.toString((long) tempVal);
        if(length - sigFigs >= 0 && value > 1.0){
            long val = Long.parseLong(substring.substring(0, sigFigs));
            if(length - sigFigs > 0){
                prettyString = val + "e" + (length - sigFigs);
            }else{
                prettyString = val + "";
            }
        }else{
            if(value < 1.0){ 
                int exponent = 0;
                for(int i = 0; value < 1.0; i++){
                    value *= 10.0;
                    exponent = i;
                }
                exponent++;
                if(sigFigs > 1){
                    try{
                    value = Double.parseDouble(Double.toString(value).substring(0, sigFigs+1));
                    }catch(Exception e){
                        return originalValue + "";
                    }
                    prettyString = value + "e" + (-exponent);
                }else{
                    long tempValue = Long.parseLong(Long.toString((long) value).substring(0, sigFigs));
                    prettyString = tempValue + "e" + (-exponent);
                }
            }else{
                System.out.println("LENGTH = " + tempVal);
                scaleFactor = Math.pow(10.0, sigFigs);
                double subValue = 0.0;
                
                if((length - sigFigs) > 0){
                    subValue = Math.round(tempVal * scaleFactor) / scaleFactor / (scaleFactor);
                }else{
                    try{
                        prettyString = Double.toString(value).substring(0, sigFigs+1);
                    }catch(Exception e){
                        return originalValue + "";
                    }
                    return prettyString;
                }
                prettyString = Double.toString(subValue);
            }
        }
        return prettyString;
    }
}
