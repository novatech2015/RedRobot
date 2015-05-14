/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.resources;

import finaleddrobot.utility.SerialDevice;
import finaleddrobot.utility.TerminalExecutor;
import java.io.IOException;

/**
 *
 * @author mallory
 */
public class Resources {
    
    public static TerminalExecutor m_exec;
    public static SerialDevice m_arduino;
    
    public static void init() throws IOException{
        m_exec = new TerminalExecutor();
        m_arduino = new SerialDevice("ttyUSB0");
    }
}
