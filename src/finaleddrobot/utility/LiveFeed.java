/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.utility;

import finaleddrobot.resources.Resources;
import finaleddrobot.resources.RobotConstants;

/**
 *
 * @author mallory
 */
public class LiveFeed {
    
    public LiveFeed(){
        
    }
    
    public void start(){
        Resources.m_exec.executeCommand("streaming " + RobotConstants.twitchKey);
    }
    
    public void stop(){
        Resources.m_exec.executeCommand("killall ffmpeg; killall ffserver");
    }
}
