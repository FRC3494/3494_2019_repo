package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;

public class NavX {

    private static NavX INSTANCE = new NavX();
    private AHRS ahrs;
    private double resetValue;

    private NavX() {
        this.ahrs = new AHRS(SerialPort.Port.kMXP);
        this.resetFusedHeading();
    }

    public static NavX getInstance() {
        return INSTANCE;
    }

    public double getFusedHeading() {
        double fusedHeading = (ahrs.getFusedHeading() - resetValue);
        if (fusedHeading < 0) {
            return 360 + fusedHeading;
        }
        return fusedHeading;
    }

    public void resetFusedHeading() {
        resetValue = ahrs.getFusedHeading();
    }

    /*
     * periodic had to be commented out because NavX was moved to sensors.
     * This code is saved so it can be reused.
     *
     * public void periodic() {
     * if (SmartDashboard.getBoolean("Display navX data?", false)) {
     * System.out.println("The robot angle is " + this.getFusedHeading());
     * }
     * }
     */
}

