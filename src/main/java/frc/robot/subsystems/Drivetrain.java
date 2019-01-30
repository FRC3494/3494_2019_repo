package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.drive.Drive;
import frc.robot.sensors.PDP;

public class Drivetrain extends Subsystem {
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    /**
     * Master CANSparkMax, left side.
     */
    private CANSparkMax driveLeftMaster;
    /**
     * Follower CANSparkMax, left side.
     */
    private CANSparkMax driveLeftFollowOne;
    /**
     * Additional follower CANSparkMax, left side.
     */
    private CANSparkMax driveLeftFollowTwo;

    /**
     * Master CANSparkMax, right side.
     */
    private CANSparkMax driveRightMaster;
    /**
     * Follower CANSparkMax, right side.
     */
    private CANSparkMax driveRightFollowOne;
    /**
     * Additional follower CANSparkMax, right side.
     */
    private CANSparkMax driveRightFollowTwo;


    private static Drivetrain INSTANCE = new Drivetrain();

    private Drivetrain() {
        this.driveLeftMaster = new CANSparkMax(RobotMap.DRIVETRAIN.leftMasterChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

        this.driveLeftFollowOne = new CANSparkMax(RobotMap.DRIVETRAIN.leftFollower1Channel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowOne.follow(driveLeftMaster);

        this.driveLeftFollowTwo = new CANSparkMax(RobotMap.DRIVETRAIN.leftFollower2Channel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowTwo.follow(driveLeftMaster);

        this.driveRightMaster = new CANSparkMax(RobotMap.DRIVETRAIN.rightMasterChannel, CANSparkMaxLowLevel.MotorType.kBrushless);

        this.driveRightFollowOne = new CANSparkMax(RobotMap.DRIVETRAIN.rightFollower1Channel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveRightFollowOne.follow(driveRightMaster);

        this.driveRightFollowTwo = new CANSparkMax(RobotMap.DRIVETRAIN.rightFollower2Channel, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveRightFollowTwo.follow(driveRightMaster);
    }

    /**
     * Tank drive.
     *
     * @param leftSpeed  Speed of left side.
     * @param rightSpeed Speed of right side.
     */
    public void tankDrive(double leftSpeed, double rightSpeed) {

        this.driveLeftMaster.set(leftSpeed);
        this.driveRightMaster.set(rightSpeed);

        if (!checkMotorsGoingSameDirection(leftSpeed, rightSpeed)) {
            //add debug method here to fix the problem
        }
    }

    private enum Direction{
        kBackward, //corresponds to 0
        kNeutral, //corresponds to 1
        kForward; //corresponds to 2
    }

    private boolean checkMotorsGoingSameDirection(double leftSpeed, double rightSpeed){
        if(getLeftMasterDirection()!=getLeftFollowOneDirection() ||
           getLeftMasterDirection()!=getLeftFollowTwoDirection())
        {
            return false;
        }

        if((getLeftMasterDirection().ordinal()-1)!=Math.signum(leftSpeed)){
            return false;
        }

        if(getRightMasterDirection()!=getRightFollowOneDirection() ||
           getRightMasterDirection()!=getRightFollowTwoDirection())
        {
            return false;
        }

        if((getRightMasterDirection().ordinal()-1)!=Math.signum(rightSpeed)){
            return false;
        }
    }

    public Direction getLeftMasterDirection() {
        double motorDirection = Math.signum(this.driveLeftMaster.getEncoder().getVelocity());

        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    //add method to check if encoders are null
    public Direction getLeftFollowOneDirection() {
        double motorDirection = Math.signum(this.driveLeftFollowOne.getEncoder().getVelocity());
        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    public Direction getLeftFollowTwoDirection() {
        double motorDirection = Math.signum(this.driveLeftFollowTwo.getEncoder().getVelocity());
        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    public Direction getRightMasterDirection() {
        double motorDirection = Math.signum(this.driveRightMaster.getEncoder().getVelocity());
        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    public Direction getRightFollowOneDirection() {
        double motorDirection = Math.signum(this.driveRightFollowOne.getEncoder().getVelocity());
        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    public Direction getRightFollowTwoDirection() {
        double motorDirection = Math.signum(this.driveRightFollowTwo.getEncoder().getVelocity());
        if (motorDirection == 1) {
            return Direction.kForward;
        }

        if (motorDirection == -1) {
            return Direction.kBackward;
        }

        return Direction.kNeutral;
    }

    public void stop() {
        this.tankDrive(0, 0);
    }

    @Override
    public void periodic() {
        if (SmartDashboard.getBoolean("Display Drivetrain data?", false)) {
            System.out.println("The left side: " + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftMasterChannel) + ", "
                    + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower1Channel) + ", " +
                    PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower2Channel));
            System.out.println("The left side: " + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftMasterChannel) + ", "
                    + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower1Channel) + ", " +
                    PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.leftFollower2Channel));
        }
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
