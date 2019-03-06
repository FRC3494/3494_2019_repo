package frc.robot.sensors;

import edu.wpi.first.wpilibj.AnalogInput;
import frc.robot.RobotMap;

public class PressureSensor {
    private static final PressureSensor INSTANCE = new PressureSensor(RobotMap.PRESSURE_SENSOR_PORT);

    private AnalogInput ai;

    public PressureSensor(int inputPin) {
        this.ai = new AnalogInput(inputPin);
    }

    private double getVoltageOut() {
        return ai.getVoltage();
    }

    /**
     * Return the pressure, in PSI. This function was made by a really confident linear regression.
     *
     * @return Measured pressure, in PSI.
     */
    public double getPressure() {
        return 31.8 * this.getVoltageOut() - 8.07;
    }

    public static PressureSensor getInstance() {
        return INSTANCE;
    }
}
