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
public class MiFareStringBuilder {
    
    private int maxSize = 720;
    private MiFareString m_mifareString;
    
    
    public MiFareStringBuilder(){
        m_mifareString = new MiFareString();
    }
    
    public void appendString(String value) throws Exception{
        m_mifareString.appendString(value);
    }
    
    public MiFareString getMifareString(){
        return m_mifareString;
    }
    
    
    
    
    
    
    public class MiFareString{
        
        private String raw_mfstring = "";
        private String processed_mfstring = new String();
        private final String k_blockZero =   "\u00d7\u00fb\u0012\u00cb\u00f5\u0008\u0004\u0000\u0062\u0063\u0064\u0065\u0066\u0067\u0068\u0069" +
                                            "\u0014\u0001\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1" +
                                            "\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1\u0003\u00e1" +
                                            "\u0000\u0000\u0000\u0000\u0000\u0000\u0078\u0077\u0088\u00c1\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff";
        private final String k_blockSuffix = "\u0000\u0000\u0000\u0000\u0000\u0000\u007f\u0007\u0088\u0040\u00ff\u00ff\u00ff\u00ff\u00ff\u00ff";
        
        private MiFareString(){
            raw_mfstring = "";
        }
        
        public String getValueOf(){
            processMfString();
            return processed_mfstring;
        }
        
        private void appendString(String value) throws Exception{
            if(raw_mfstring.length() + value.length() > maxSize){
                value = value.substring(0, maxSize-raw_mfstring.length()-1);
                raw_mfstring += value;
                throw new Exception("String \"" + value + "\" was too large. String was automatically shortened.");
            }else{
                raw_mfstring += value;
            }
        }
        
        private void processMfString(){
            String stringBuffer = raw_mfstring.substring(0);
            if(raw_mfstring.length() > 720){
                stringBuffer = raw_mfstring.substring(0, 720);
            }else if(raw_mfstring.length() < 720){
                int missingChars = 720 - raw_mfstring.length();
                for(int i = 0; i < missingChars; i++){
                    stringBuffer += "K";
                    System.out.println(1);
                }
            }
            System.out.println("STRINGBUFF " + stringBuffer.length());
            int stepSize = 48;
            processed_mfstring =    k_blockZero;
            for(int i = 0; i < 720/stepSize; i++){
                processed_mfstring += stringBuffer.substring(stepSize * i, stepSize * (i+1));
                processed_mfstring += k_blockSuffix;
                System.out.println(processed_mfstring.length());
            }
            
        }
    }
}
