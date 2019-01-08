package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.commands.drive.Drive;

public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static Drivetrain INSTANCE = new Drivetrain();

    private Drivetrain() {
    }

    /**
     * Tank drive.
     *
     * @param leftSpeed  Speed of left side.
     * @param rightSpeed Speed of right side.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {
    }

    public static Drivetrain getInstance() {
        return INSTANCE;
    }

    @Override
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        // setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new Drive());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
