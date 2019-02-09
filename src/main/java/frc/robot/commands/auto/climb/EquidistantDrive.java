package frc.robot.commands.auto.climb;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;

public class EquidistantDrive extends Command {
    /**
     * The distance to drive both sides, in encoder rotations.
     */
    private double distance;
    private CANPIDController leftPIDController;
    private CANPIDController rightPIDController;

    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    public EquidistantDrive(double d) {
        requires(Drivetrain.getInstance());
        // Convert d (feet) to motor revolutions
        this.distance = d * (1.0d / RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE) * (1.0d / RobotMap.DRIVETRAIN.GEAR_RATIO);
    }

    @Override
    protected void initialize() {
        SmartDashboard.putBoolean("Debug EquidistantDrive", false);
        // Done here in case this command is instantiated before it's scheduled to start running
        // (entirely possible with buttons command groups etc.)
        leftPIDController = Drivetrain.getInstance().getLeftPID();
        rightPIDController = Drivetrain.getInstance().getRightPID();
        leftEncoder = Drivetrain.getInstance().getLeftEncoder();
        rightEncoder = Drivetrain.getInstance().getRightEncoder();
        // Tune controllers. Must be done here because every invocation of get[Side]PID() produces a brand-spanking new
        // PID controller object.
        // Blame REV.
        leftPIDController.setP(1.0);
        leftPIDController.setI(0);
        leftPIDController.setIZone(0);
        leftPIDController.setD(1.0);
        leftPIDController.setFF(0);
        leftPIDController.setOutputRange(-1, 1);

        rightPIDController.setP(1.0);
        rightPIDController.setI(0);
        rightPIDController.setIZone(0);
        rightPIDController.setD(1.0);
        rightPIDController.setFF(0);
        rightPIDController.setOutputRange(-1, 1);
    }

    @Override
    protected void execute() {
        leftPIDController.setReference(distance, ControlType.kPosition);
        rightPIDController.setReference(distance, ControlType.kPosition);
        if (SmartDashboard.getBoolean("Debug EquidistantDrive", false)) {
            SmartDashboard.putNumber("SetPoint", distance);
            SmartDashboard.putNumber("Left encoder pos", leftEncoder.getPosition());
            SmartDashboard.putNumber("Right encoder pos", rightEncoder.getPosition());
        }
    }

    @Override
    protected boolean isFinished() {
        return inRange(Drivetrain.getInstance().getLeftAveragePosition(), distance, 0.05) &&
                inRange(Drivetrain.getInstance().getRightAveragePosition(), distance, 0.05);
    }

    private static boolean inRange(double test, double target, double percentTolerance) {
        return (test >= target - (target * percentTolerance) && test <= target + (target * percentTolerance));
    }
}
