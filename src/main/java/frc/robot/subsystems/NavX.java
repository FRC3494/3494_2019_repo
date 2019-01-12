package frc.robot.subsystems;


import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;

public class NavX extends Subsystem {

    private NavX INSTANCE = new NavX();
    private AHRS ahrs;
    private double resetValue;

    private NavX() {
        ahrs = new AHRS(SerialPort.Port.kMXP);
        resetValue = 0;
    }

    public NavX getInstance() {
        return INSTANCE;
    }

    public double getFusedHeading() {
        double fusedHeading = (double) (ahrs.getFusedHeading() - resetValue);
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

    public void periodic() {
        if (SmartDashboard.getBoolean("Display navX data?", false)) {
            System.out.println("The robot angle is " + this.getFusedHeading());
        }
    }


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

