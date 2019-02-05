package frc.robot.subsystems;

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

    private CANSparkMax[] driveLeftMotors;
    private CANSparkMax[] driveRightMotors;

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

        this.driveLeftMotors = new CANSparkMax[]{this.driveLeftMaster, this.driveLeftFollowOne, this.driveLeftFollowTwo};
        this.driveRightMotors = new CANSparkMax[]{this.driveRightMaster, this.driveRightFollowOne, this.driveRightFollowTwo};
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
    private boolean updateMotorSpeedsData(double leftSpeed, double rightSpeed) {
        boolean displayErrors = SmartDashboard.getBoolean("Display Drivetrain motor data?", false);

        boolean isLeftMotorsOppositeDirection = (Math.signum(driveLeftMaster.getEncoder().getVelocity()) != Math.signum(driveLeftFollowOne.getEncoder().getVelocity()) ||
                Math.signum(driveLeftMaster.getEncoder().getVelocity()) != Math.signum(driveLeftFollowTwo.getEncoder().getVelocity()));
        boolean isLeftMasterInverted = Math.signum(driveLeftMaster.getEncoder().getVelocity()) != Math.signum(leftSpeed);
        boolean isRightMasterInverted = Math.signum(driveRightMaster.getEncoder().getVelocity()) != Math.signum(rightSpeed);
        boolean isRightMotorsOppositeDirection = (Math.signum(driveRightMaster.getEncoder().getVelocity()) != Math.signum(driveRightFollowOne.getEncoder().getVelocity()) ||
                Math.signum(driveRightMaster.getEncoder().getVelocity()) != Math.signum(driveRightFollowTwo.getEncoder().getVelocity()));

        if (displayErrors) {
            SmartDashboard.putBoolean("Left motors different directions", isRightMotorsOppositeDirection);
            SmartDashboard.putBoolean("LeftMotorInverted", isRightMasterInverted);
            SmartDashboard.putBoolean("Right motors different directions", isRightMotorsOppositeDirection);
            SmartDashboard.putBoolean("RightMotorInverted", isRightMasterInverted);
        }

        if (isLeftMasterInverted || isLeftMotorsOppositeDirection || isRightMasterInverted || isRightMotorsOppositeDirection) {
            return false;
        }
        return true;
    }

    //checkMotorsEngaged() checks to make sure that each left motor is going roughly the same speed as the other left motors
    //same for right side
    private boolean checkMotorsEngaged() {
        boolean displayErrors = SmartDashboard.getBoolean("Display Drivetrain motor data?", false);

        double dif = driveLeftMaster.getEncoder().getVelocity() - driveLeftFollowOne.getEncoder().getVelocity();
        boolean isMotorLeftFollowOneSpeedNotConsistent = Math.abs(dif / driveLeftMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE;

        dif = driveLeftMaster.getEncoder().getVelocity() - driveLeftFollowTwo.getEncoder().getVelocity();
        boolean isMotorLeftFollowTwoSpeedNotConsistent = Math.abs(dif / driveLeftMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE;

        dif = driveRightMaster.getEncoder().getVelocity() - driveRightFollowOne.getEncoder().getVelocity();
        boolean isMotorRightFollowOneSpeedNotConsistent = Math.abs(dif / driveRightMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE;

        dif = driveRightMaster.getEncoder().getVelocity() - driveRightFollowTwo.getEncoder().getVelocity();
        boolean isMotorRightFollowTwoSpeedNotConsistent = Math.abs(dif / driveRightMaster.getEncoder().getVelocity()) > RobotMap.DRIVETRAIN.TOLERANCE_AXLE;

        if (displayErrors) {
            SmartDashboard.putBoolean("Left master motor speed very different from follow one motor", isMotorLeftFollowOneSpeedNotConsistent);
            SmartDashboard.putBoolean("Left master motor speed very different from follow two motor", isMotorLeftFollowTwoSpeedNotConsistent);
            SmartDashboard.putBoolean("Right master motor speed very different from follow one motor", isMotorRightFollowOneSpeedNotConsistent);
            SmartDashboard.putBoolean("Right master motor speed very different from follow two motor", isMotorRightFollowTwoSpeedNotConsistent);
        }

        if (isMotorLeftFollowOneSpeedNotConsistent || isMotorLeftFollowTwoSpeedNotConsistent || isMotorRightFollowOneSpeedNotConsistent || isMotorRightFollowTwoSpeedNotConsistent) {
            return false;
        }

        return true;
    }

    //checkMotorCurrent() checks that each motor is drawing roughly the expected amount of current
    private boolean checkMotorCurrent() {
        boolean displayErrors = SmartDashboard.getBoolean("Display Drivetrain motor data?", false);
        boolean errorsExist = false;


        for (int i = 0; i < 3; i++) {
            double dif = driveLeftMotors[i].getOutputCurrent() - RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT;
            boolean drawUnusualAmountsCurrent = Math.abs(dif / RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT) < RobotMap.DRIVETRAIN.TOLERANCE_CURRENT;
            SmartDashboard.putBoolean("The " + i + " left motor is drawing an unusual amount of current.", drawUnusualAmountsCurrent);
            if (drawUnusualAmountsCurrent) {
                errorsExist = true;
            }
        }

        for (int i = 0; i < 3; i++) {
            double dif = driveRightMotors[i].getOutputCurrent() - RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT;
            boolean drawUnusualAmountsCurrent = Math.abs(dif / RobotMap.DRIVETRAIN.EXPECTED_FREE_CURRENT) < RobotMap.DRIVETRAIN.TOLERANCE_CURRENT;
            SmartDashboard.putBoolean("The " + i + " right motor is drawing an unusual amount of current.", drawUnusualAmountsCurrent);
            if (drawUnusualAmountsCurrent) {
                errorsExist = true;
            }
        }

        return !errorsExist;
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
