/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.packages;

import finaleddrobot.actuators.StepperMotor;
import finaleddrobot.actuators.StepperMotor.Direction;
import finaleddrobot.resources.RobotConstants;
import java.util.Timer;

/**
 *
 * @author mallory
 */
public class Hatch {
    
    private StepperMotor stepper;
    private long startTime = 0;   
    
    public Hatch(int AIN2, int AIN1, int BIN2, int BIN1){
        stepper = new StepperMotor(AIN2, AIN1, BIN2, BIN1);
    }
    
    public void cycleRight(double degrees){
        byte steps = (byte) Math.round(Math.abs(degrees % 360) / 1.8);
        stepper.step(steps, StepperMotor.Direction.Forward, StepperMotor.Style.Double);
    }
    
    public void cycleLeft(double degrees){
        byte steps = (byte) Math.round(Math.abs(degrees % 360) / 1.8);
        stepper.step(steps, StepperMotor.Direction.Backward, StepperMotor.Style.Double);
    }
    
    public void oneStep(Direction direction){
        stepper.oneStep(direction, StepperMotor.Style.Double);
    }
    
    public boolean isHatchOpen(){
        if(this.startTime == 0){
            startTime = System.currentTimeMillis();
        }
        return (System.currentTimeMillis() - startTime > RobotConstants.hatchOpenTime);
    }
}
