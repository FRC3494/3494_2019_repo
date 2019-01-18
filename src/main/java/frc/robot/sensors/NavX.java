package frc.robot.sensors;

import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class NavX {

    private NavX INSTANCE = new NavX();
    private AHRS ahrs;
    private double resetValue;

    private NavX() {
        this.ahrs = new AHRS(SerialPort.Port.kMXP);
        resetValue = 0;
    }

    public NavX getInstance() {
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
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * periodic had to be commented out because NavX was moved to sensors.
     * This code is saved so it can be reused.
     * <p>
     * public void periodic() {
     * if (SmartDashboard.getBoolean("Display navX data?", false)) {
     * System.out.println("The robot angle is " + this.getFusedHeading());
     * }
     * }
     */
}

