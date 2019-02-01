package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class PressureSensor {
    private AnalogInput ai;
    private static final double VCC = 5.0;

    private PressureSensor(int inputPin) {
        this.ai = new AnalogInput(inputPin);
    }

    private double getVoltageOut() {
        return ai.getVoltage();
    }

    public double getPressure() {
        return (250 * (this.getVoltageOut() / VCC)) - 25;
    }
}
