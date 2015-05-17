package finaleddrobot.utility;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * TODO : Clear Unused Objects to save memory
 * @author mallory
 */
public class SerialDevice {
    
    private String m_address = "/dev/ttyUSB0";
    private TerminalExecutor exec = new TerminalExecutor();
    private FileReader fr;
    private FileWriter fw;
    
    public SerialDevice() throws FileNotFoundException, IOException{
        File file = new File(m_address);
        fr = new FileReader(file);
        fw = new FileWriter(file);
        exec.executeCommand("stty raw -echo < " + m_address);
    }
    
    public SerialDevice(String address) throws FileNotFoundException, IOException{
        this.m_address = address;
        File file = new File(m_address);
        fr = new FileReader(file);
        fw = new FileWriter(file);
    }
    
    public void write(String string) throws IOException{
        exec.executeCommand("echo " + string + "> " + m_address);
    }
    
    public char[] read() throws IOException{
        
        char[] buffer = new char[512];
        fr.read(buffer);
        return buffer;
    }
}
