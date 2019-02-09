package frc.robot.commands.auto.climb;

import com.revrobotics.CANPIDController;
import com.revrobotics.ControlType;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;

public class EquidistantDrive extends Command {
    /**
     * The distance to drive both sides, in encoder rotations.
     */
    private double distance;

    public EquidistantDrive(double d) {
        requires(Drivetrain.getInstance());
        // Convert d (feet) to motor revolutions
        this.distance = d * (1.0d / RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE) * (1.0d / RobotMap.DRIVETRAIN.GEAR_RATIO);
    }

    @Override
    protected void initialize() {
        CANPIDController leftPIDController = Drivetrain.getInstance().getLeftPID();
        CANPIDController rightPIDController = Drivetrain.getInstance().getRightPID();

        leftPIDController.setReference(distance, ControlType.kPosition);
        rightPIDController.setReference(distance, ControlType.kPosition);
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
