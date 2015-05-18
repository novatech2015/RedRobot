/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.resources;

import finaleddrobot.sensors.Arduino;
import finaleddrobot.sensors.BMP180;
import finaleddrobot.sensors.HIH6130;
import finaleddrobot.sensors.PN532;
import finaleddrobot.sensors.TMP102;
import finaleddrobot.utility.MiFareStringBuilder;
import finaleddrobot.utility.TerminalExecutor;
import java.io.IOException;

/**
 *
 * @author mallory
 */
public class Resources {
    
    public static TerminalExecutor m_exec;
    public static TMP102 m_tmp102;
    public static HIH6130 m_hih6130;
    public static BMP180 m_bmp180;
    public static Arduino m_arduino;
    public static PN532 m_pn532;
    public static MiFareStringBuilder m_mifareStringBuilder;
    
    public static void init() throws IOException{
        m_exec = new TerminalExecutor();
        m_tmp102 = new TMP102();
        m_hih6130 = new HIH6130();
        m_bmp180 = new BMP180();
        m_arduino = new Arduino("/dev/ttyUSB0");
        m_pn532 = new PN532();
        m_mifareStringBuilder = new MiFareStringBuilder();
    }
}
