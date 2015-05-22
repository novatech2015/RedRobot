package finaleddrobot.actuators;

import finaleddrobot.utility.TerminalExecutor;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <p>Note : Pin Numbers assume standard Wiring Pi Pin Numbers. Refer to 
 * <a href="http://wiringpi.com/pins/">http://wiringpi.com/pins/</a>
 * for more information.
 * </p>
 * @author mallory
 */
public class StepperMotor {
    
    int microsteps = 8;
    
    int pwmA = 0;
    int aIn2 = 0;
    int aIn1 = 0;
    int pwmB = 0;
    int bIn2 = 0;
    int bIn1 = 0;
    
    double sec_per_step = 0.1;
    double revSteps = 200;
    int steppingCounter = 0;
    int currentStep = 0;
    short pwm_a = 255, pwm_b = 255;
    
    TerminalExecutor exec = new TerminalExecutor();
    
    public StepperMotor(int PWMA, int AIN2, int AIN1, int PWMB, int BIN2, int BIN1){
        this.pwmA = PWMA;
        this.aIn2 = AIN2;
        this.aIn1 = AIN1;
        this.pwmB = PWMB;
        this.bIn2 = BIN2;
        this.bIn1 = BIN1;
        
        exec.executeCommand("gpio mode " + this.aIn1 + " out");
        exec.executeCommand("gpio mode " + this.aIn2 + " out");
        exec.executeCommand("gpio mode " + this.bIn1 + " out");
        exec.executeCommand("gpio mode " + this.bIn2 + " out");
        exec.executeCommand("gpio mode " + this.pwmA + " pwm");
        exec.executeCommand("gpio mode " + this.pwmB + " pwm");
        
    }
    
    public StepperMotor(int AIN2, int AIN1, int BIN2, int BIN1){
        this.pwmA = -9001;
        this.aIn2 = AIN2;
        this.aIn1 = AIN1;
        this.pwmB = -9001;
        this.bIn2 = BIN2;
        this.bIn1 = BIN1;
        
        exec.executeCommand("gpio mode " + this.aIn1 + " out");
        exec.executeCommand("gpio mode " + this.aIn2 + " out");
        exec.executeCommand("gpio mode " + this.bIn1 + " out");
        exec.executeCommand("gpio mode " + this.bIn2 + " out");
        
    }
    
    public void setSpeed(double rpm){
        this.sec_per_step = 60.0 / (this.revSteps * rpm);
        this.steppingCounter = 0;
    }
    
    public int oneStep(Direction dir, Style style){
        this.pwm_a = this.pwm_b = 255;
        
        if(style.equals(Style.Single)){
            //TODO
        }
        if(style.equals(Style.Double)){
            if((this.currentStep / (this.microsteps/2) % 2) == 0){
                if(dir.equals(Direction.Forward)){
                    this.currentStep += this.microsteps/2;
                }else{
                    this.currentStep -= this.microsteps/2;
                }
            }else{
                if(dir.equals(Direction.Forward)){
                    this.currentStep += this.microsteps;
                }else{
                    this.currentStep -= this.microsteps;
                }
            }
        }
        if(style.equals(Style.Interleave)){
            //TODO
        }
        if(style.equals(Style.Microstep)){
            //TODO
        }
        
        this.currentStep += this.microsteps * 4;
        this.currentStep %= this.microsteps * 4;
        
        if(this.pwmA > 0 && this.pwmB > 0){
            exec.executeCommand("gpio pwm " + this.pwmA + " " + this.pwm_a*4);
            exec.executeCommand("gpio pwm " + this.pwmB + " " + this.pwm_b*4);
        }
        
        
        byte[] coils;
        coils = new byte[] {0,0,0,0};
        
        if(style.equals(Style.Microstep)){
            //TODO
        }else{
            byte[][] steps2coils;
            steps2coils = new byte[][] { 	new byte[] {1, 0, 0, 0}, 
                                                new byte[] {1, 1, 0, 0},
                                                new byte[] {0, 1, 0, 0},
                                                new byte[] {0, 1, 1, 0},
                                                new byte[] {0, 0, 1, 0},
                                                new byte[] {0, 0, 1, 1},
                                                new byte[] {0, 0, 0, 1},
                                                new byte[] {1, 0, 0, 1} };
            coils = steps2coils[this.currentStep/(this.microsteps/2)];
        
            exec.executeCommand("gpio write " + this.aIn2 + " " +coils[0]);
            exec.executeCommand("gpio write " + this.bIn1 + " " +coils[1]);
            exec.executeCommand("gpio write " + this.aIn1 + " " +coils[2]);
            exec.executeCommand("gpio write " + this.bIn2 + " " +coils[3]);
            
            
        }
        
        return this.currentStep;
    }
    
    public void step(byte steps, Direction direction, Style stepStyle){
        double s_per_s = this.sec_per_step;
        short latestStep = 0;
        
        if(stepStyle.equals(Style.Interleave)){
            s_per_s = s_per_s / 2.0;
        }
        if(stepStyle.equals(Style.Microstep)){
            s_per_s /= this.microsteps;
            steps *= this.microsteps;
        }
        
        for(int i = 0; i < steps; i++){
            latestStep = (short) this.oneStep(direction, stepStyle);
            try {
                Thread.sleep((long) (s_per_s*1000));
            } catch (InterruptedException ex) {
                Logger.getLogger(StepperMotor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        if(stepStyle.equals(Style.Microstep)){
            
        }
        
    }
    
    public static class Style{
        
        private byte m_style = 0;
        
        public static final Style Single = new Style((byte) 1);
        public static final Style Double = new Style((byte) 2);
        public static final Style Interleave = new Style((byte) 3);
        public static final Style Microstep = new Style((byte) 4);
        
        private Style(byte style){
            this.m_style = style;
        }
        
        public boolean equals(Style object){
            return object.m_style == this.m_style;
        }
    }
    
    public static class Direction{
        private byte m_direction = 0;
        
        public static final Direction Forward = new Direction((byte) 1);
        public static final Direction Backward = new Direction((byte) 2);
        public static final Direction Brake = new Direction((byte) 3);
        public static final Direction Release = new Direction((byte) 4);
        
        private Direction(byte dir){
            this.m_direction = dir;
        }
        
        public boolean equals(Direction object){
            return object.m_direction == this.m_direction;
        }
    }
    
    
}
