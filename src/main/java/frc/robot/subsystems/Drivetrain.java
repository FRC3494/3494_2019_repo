package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.commands.drive.Drive;
import frc.robot.sensors.NavX;
import frc.robot.sensors.PDP;

public class Drivetrain extends PIDSubsystem {
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

    private double PIDOutput = 0;


    private static Drivetrain INSTANCE = new Drivetrain();

    private Drivetrain() {
        super(1, 0, 0);
        this.setOutputRange(-1, 1);
        this.setPercentTolerance(RobotMap.DRIVETRAIN.PID_PERCENT_TOLERANCE);
        this.getPIDController().setContinuous(true);
        this.setInputRange(0, 360);

        this.driveLeftMaster = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_MASTER_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);

        this.driveLeftFollowOne = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_ONE_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowOne.follow(driveLeftMaster);

        this.driveLeftFollowTwo = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_TWO_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowTwo.follow(driveLeftMaster);

        this.driveRightMaster = new CANSparkMax(RobotMap.DRIVETRAIN.RIGHT_MASTER_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);

        this.driveRightFollowOne = new CANSparkMax(RobotMap.DRIVETRAIN.RIGHT_FOLLOWER_ONE_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveRightFollowOne.follow(driveRightMaster);

        this.driveRightFollowTwo = new CANSparkMax(RobotMap.DRIVETRAIN.RIGHT_FOLLOWER_TWO_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
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

    //returns feet per sec velocity
    public double getLeftAverageVelocity() {
        double sum = this.driveLeftMaster.getEncoder().getVelocity() + this.driveLeftFollowOne.getEncoder().getVelocity() + this.driveLeftFollowTwo.getEncoder().getVelocity();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE / 60;
    }

    //returns feet per sec velocity
    public double getRightAverageVelocity() {
        double sum = this.driveRightMaster.getEncoder().getVelocity() + this.driveRightFollowOne.getEncoder().getVelocity() + this.driveRightFollowTwo.getEncoder().getVelocity();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE / 60;
    }

    //returns in feet
    public double getLeftAveragePosition() {
        double sum = this.driveLeftMaster.getEncoder().getPosition() + this.driveLeftFollowOne.getEncoder().getPosition() + this.driveLeftFollowTwo.getEncoder().getPosition();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE;
    }

    //returns in feet
    public double getRightAveragePosition() {
        double sum = this.driveRightMaster.getEncoder().getPosition() + this.driveRightFollowOne.getEncoder().getPosition() + this.driveRightFollowTwo.getEncoder().getPosition();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE;
    }

    public double getLeftMasterCurrent() {
        return this.driveLeftMaster.getOutputCurrent();
    }

    public double getLeftFollowOneCurrent() {
        return this.driveLeftFollowOne.getOutputCurrent();
    }

    public double getLeftFollowTwoCurrent() {
        return this.driveLeftFollowTwo.getOutputCurrent();
    }

    public double getRightMasterCurrent() {
        return this.driveRightMaster.getOutputCurrent();
    }

    public double getRightFollowOneCurrent() {
        return this.driveRightFollowOne.getOutputCurrent();
    }

    public double getRightFollowTwoCurrent() {
        return this.driveRightFollowTwo.getOutputCurrent();
    }

    public void stop() {
        this.tankDrive(0, 0);
    }

    @Override
    public void periodic() {
        if (SmartDashboard.getBoolean("Display Drivetrain data?", false)) {
            System.out.println("The left side: " + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_MASTER_CHANNEL) + ", "
                    + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_ONE_CHANNEL) + ", " +
                    PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_TWO_CHANNEL));
            System.out.println("The left side: " + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_MASTER_CHANNEL) + ", "
                    + PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_ONE_CHANNEL) + ", " +
                    PDP.getInstance().getCurrent(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_TWO_CHANNEL));
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

    public double getPIDOutput() {
        return this.PIDOutput;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    protected double returnPIDInput() {
        return NavX.getInstance().getFusedHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        this.PIDOutput = output;
    }
}
