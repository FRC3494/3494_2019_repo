package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class HRLVMaxSonar {

    public static final double VOLTS_PER_MM = 5.0D / 5120.0D;

    private AnalogInput ai;

    public HRLVMaxSonar(int pin) {
        this.ai = new AnalogInput(pin);
    }

    public double getDistance() {
        return this.ai.getVoltage() * (1.0 / VOLTS_PER_MM);
    }
}
