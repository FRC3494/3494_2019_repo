package frc.robot.subsystems;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.drive.Drive;

public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static Drivetrain INSTANCE = new Drivetrain();

    public static Drivetrain getInstance() {
        return INSTANCE;
    }

    private Drivetrain() {
    }

    /**
     * Tank drive.
     *
     * @param leftSpeed  Speed of left side.
     * @param rightSpeed Speed of right side.
     */
    public void tankDrive(double leftPower, double rightPower)
    {

        if(!checkTankDriveNotBroken(leftPower,rightPower)){
            this.stop();
        }
    }

    //this checks to make sure that if the robot is told to go forward, its actually traveling forward instead of backward
    private boolean checkTankDriveNotBroken(double leftPower, double rightPower){
        int leftCounter = 0;
        leftCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftMasterChannel));
        leftCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower1Channel));
        leftCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower2Channel));

        if (Math.abs(leftCounter)!=3 || Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftMasterChannel)) != Math.signum(leftPower)){
            System.out.println("One of the left motors is jammed! RIP");
            return false;
        }

        int rightCounter = 0;
        rightCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.rightMasterChannel));
        rightCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.rightFollower1Channel));
        rightCounter += Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.rightFollower2Channel));

        if (Math.abs(rightCounter)!=3 || Math.signum(PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.rightMasterChannel)) != Math.signum(leftPower)){
            System.out.println("One of the left motors is jammed! RIP");
            return false;
        }
        return true;
    }

    public void stop()
    {
        this.tankDrive(0,0);
    }

    @Override
    public void periodic() {

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
