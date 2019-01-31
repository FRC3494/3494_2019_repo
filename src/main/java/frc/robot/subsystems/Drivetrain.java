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

    private CANSparkMax[] driveLeftMotors = {this.driveLeftMaster, this.driveLeftFollowOne, this.driveLeftFollowTwo};
    private CANSparkMax[] driveRightMotors = {this.driveRightMaster, this.driveRightFollowOne, this.driveRightFollowTwo};

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
    }

    //checkMotorSpeeds() checks to make sure each of the left and right motors are going the same direction they are supposed to be going
    private boolean checkMotorSpeeds(double leftSpeed, double rightSpeed){
        if (Math.signum(driveLeftMaster.getEncoder().getVelocity())!=Math.signum(driveLeftFollowOne.getEncoder().getVelocity()) ||
        Math.signum(driveLeftMaster.getEncoder().getVelocity()) != Math.signum(driveLeftFollowTwo.getEncoder().getVelocity()))
        {
            return false;
        }
        if (Math.signum(driveLeftMaster.getEncoder().getVelocity())!=Math.signum(leftSpeed))
        {
            return false;
        }

        if (Math.signum(driveRightMaster.getEncoder().getVelocity())!=Math.signum(driveRightFollowOne.getEncoder().getVelocity()) ||
                Math.signum(driveRightMaster.getEncoder().getVelocity()) != Math.signum(driveRightFollowTwo.getEncoder().getVelocity()))
        {
            return false;
        }
        if (Math.signum(driveRightMaster.getEncoder().getVelocity())!=Math.signum(rightSpeed))
        {
            return false;
        }

        return true;
    }

    //checkMotorsEngaged() checks to make sure that each left motor is going roughly the same speed as the other left motors
    //same for right side
    private boolean checkMotorsEngaged(){
        double dif = driveLeftMaster.getEncoder().getVelocity() - driveLeftFollowOne.getEncoder().getVelocity();
        if (Math.abs(dif / driveLeftMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE){
            return false;
        }
        dif = driveLeftMaster.getEncoder().getVelocity() - driveLeftFollowTwo.getEncoder().getVelocity();
        if (Math.abs(dif / driveLeftMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE){
            return false;
        }

        dif = driveRightMaster.getEncoder().getVelocity() - driveRightFollowOne.getEncoder().getVelocity();
        if (Math.abs(dif / driveRightMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE){
            return false;
        }
        dif = driveRightMaster.getEncoder().getVelocity() - driveRightFollowTwo.getEncoder().getVelocity();
        if (Math.abs(dif / driveRightMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE){
            return false;
        }

        return true;
    }

    //checkMotorCurrent() checks that each motor is drawing roughly the expected amount of current
    private boolean checkMotorCurrent(){
        for (int i = 0; i<3; i++){
            double dif = driveLeftMotors[i].getOutputCurrent() - RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT;
            if(Math.abs(dif / RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT) < RobotMap.DRIVETRAIN.TOLERANCE_CURRENT){
                return false;
            }
        }

        for (int i = 0; i<3; i++){
            double dif = driveRightMotors[i].getOutputCurrent() - RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT;
            if(Math.abs(dif / RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT) < RobotMap.DRIVETRAIN.TOLERANCE_CURRENT){
                return false;
            }
        }

        return true;
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
