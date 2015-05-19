/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finaleddrobot.packages;

import finaleddrobot.actuators.StepperMotor;

/**
 *
 * @author mallory
 */
public class Drill {
    
    private StepperMotor stepper;
    
    public Drill(int PWMA, int AIN2, int AIN1, int PWMB, int BIN2, int BIN1){
        stepper = new StepperMotor(PWMA, AIN2, AIN1, PWMB, BIN2, BIN1);
    }
    
    public void cycleRight(double degrees){
        byte steps = (byte) Math.round(Math.abs(degrees % 360) / 1.8);
        stepper.step(steps, StepperMotor.Direction.Forward, StepperMotor.Style.Double);
    }
    
    public void cycleLeft(double degrees){
        byte steps = (byte) Math.round(Math.abs(degrees % 360) / 1.8);
        stepper.step(steps, StepperMotor.Direction.Backward, StepperMotor.Style.Double);
    }
}
