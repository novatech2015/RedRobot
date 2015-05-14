package finaleddrobot.sensors;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import finaleddrobot.utility.MiFareStringBuilder.MiFareString;
import finaleddrobot.utility.TerminalExecutor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * UNTESTED
 * @author Mr. Mallory
 */
public class PN532 {
    
    TerminalExecutor exec = new TerminalExecutor();
    FileReader fr;
    FileOutputStream fw;
    
    public PN532() throws FileNotFoundException, IOException{
        File readFile = new File("MifareReadData.mfd");
        File writeFile = new File("MifareWriteData.mfd");
        readFile.createNewFile();
        writeFile.createNewFile();
        fr = new FileReader(readFile);
        fw = new FileOutputStream(writeFile);
        
    }
    
    public void write(MiFareString value) throws IOException{
        byte[] buffer = new byte[1024];
        char[] cbuf = value.getValueOf().toCharArray();
        for(int i = 0; i < 1024; i++){
            buffer[i] = (byte) cbuf[i];
        }
        fw.write(buffer);
        fw.flush();
        String success = exec.executeCommand("nfc-mfclassic w b MifareWriteData.mfd");
    	System.out.println(success);
    }
    
    public String read() throws IOException{
	String success = exec.executeCommand("nfc-mfclassic r b MifareReadData.mfd");
	System.out.println(success);
        char[] cbuf = new char[1024];
        fr.read(cbuf);
        String data = new String(cbuf);
        
        return data;
    }
}
