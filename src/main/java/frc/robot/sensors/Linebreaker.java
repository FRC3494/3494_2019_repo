package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;

public class Linebreaker {
    private AnalogInput ai;

    public Linebreaker(int port) {
        this.ai = new AnalogInput(port);
    }

    public boolean lineBroken() {
        return this.ai.getVoltage() > 1.0D;
    }
}
