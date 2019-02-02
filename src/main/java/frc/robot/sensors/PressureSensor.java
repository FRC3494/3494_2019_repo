package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.RobotMap;

public class PressureSensor {
    private static final PressureSensor INSTANCE = new PressureSensor(RobotMap.PRESSURE_SENSOR_PORT);

    private AnalogInput ai;
    /**
     * The voltage into the sensor.
     */
    private static final double VCC = 5.0;

    public PressureSensor(int inputPin) {
        this.ai = new AnalogInput(inputPin);
    }

    private double getVoltageOut() {
        return ai.getVoltage();
    }

    /**
     * Return the pressure, in PSI. Has a tolerance of 1.5% according to REV.
     *
     * @return Measured pressure, in PSI.
     * @see "http://www.revrobotics.com/content/docs/REV-11-1107-DS.pdf"
     */
    public double getPressure() {
        return (250 * (this.getVoltageOut() / VCC)) - 25;
    }

    public static PressureSensor getInstance() {
        return INSTANCE;
    }
}
