package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
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
        this.driveLeftMaster = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_MASTER_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftMaster.setInverted(true);

        this.driveLeftFollowOne = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_ONE_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowOne.follow(driveLeftMaster);
        this.driveLeftFollowOne.setInverted(true);

        this.driveLeftFollowTwo = new CANSparkMax(RobotMap.DRIVETRAIN.LEFT_FOLLOWER_TWO_CHANNEL, CANSparkMaxLowLevel.MotorType.kBrushless);
        this.driveLeftFollowTwo.follow(driveLeftMaster);
        this.driveLeftFollowTwo.setInverted(true);

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

    public CANPIDController getLeftPID() {
        return this.driveLeftMaster.getPIDController();
    }

    public CANPIDController getRightPID() {
        return this.driveRightMaster.getPIDController();
    }

    public CANEncoder getLeftEncoder() {
        return this.driveLeftMaster.getEncoder();
    }

    public CANEncoder getRightEncoder() {
        return this.driveRightMaster.getEncoder();
    }

    /**
     * Returns the average speed of the left side encoders, in feet/second.
     *
     * @return The left side average velocity.
     */
    public double getLeftAverageVelocity() {
        double sum = this.driveLeftMaster.getEncoder().getVelocity() + this.driveLeftFollowOne.getEncoder().getVelocity() + this.driveLeftFollowTwo.getEncoder().getVelocity();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE / 60;
    }

    /**
     * Returns the average speed of the right side encoders, in feet/second.
     *
     * @return The right side average velocity.
     */
    public double getRightAverageVelocity() {
        double sum = this.driveRightMaster.getEncoder().getVelocity() + this.driveRightFollowOne.getEncoder().getVelocity() + this.driveRightFollowTwo.getEncoder().getVelocity();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE / 60;
    }

    /**
     * Returns the average position of the left side encoders, in feet.
     *
     * @return The left side average position.
     */
    public double getLeftAveragePosition() {
        double sum = this.driveLeftMaster.getEncoder().getPosition() + this.driveLeftFollowOne.getEncoder().getPosition() + this.driveLeftFollowTwo.getEncoder().getPosition();
        return sum / 3 * RobotMap.DRIVETRAIN.GEAR_RATIO * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE;
    }

    /**
     * Returns the average position of the right side encoders, in feet.
     *
     * @return The right side average position.
     */
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

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
