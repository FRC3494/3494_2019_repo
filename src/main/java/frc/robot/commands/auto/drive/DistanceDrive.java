package frc.robot.commands.auto.drive;

import com.revrobotics.CANEncoder;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;

public class DistanceDrive extends Command {

    private double dist;
    private double power;

    private CANEncoder leftEncoder;
    private CANEncoder rightEncoder;

    private static final double OUTPUT_REVS_PER_MOTOR_REV = Math.pow(RobotMap.DRIVETRAIN.GEAR_RATIO, -1);

    public DistanceDrive(double distance) {
        this(distance, 0.5);
    }

    public DistanceDrive(double distance, double speed) {
        requires(Drivetrain.getInstance());
        this.dist = distance;
        this.power = Math.abs(speed);
    }

    @Override
    protected void initialize() {
        this.leftEncoder = Drivetrain.getInstance().getLeftEncoder();
        this.leftEncoder.setPosition(0);
        // encoder now measures feet traveled
        this.leftEncoder.setPositionConversionFactor(OUTPUT_REVS_PER_MOTOR_REV * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE);
        this.rightEncoder = Drivetrain.getInstance().getRightEncoder();
        this.rightEncoder.setPosition(0);
        this.rightEncoder.setPositionConversionFactor(OUTPUT_REVS_PER_MOTOR_REV * RobotMap.DRIVETRAIN.WHEEL_CIRCUMFERENCE);
    }

    @Override
    protected void execute() {
        double avg_dist = (this.leftEncoder.getPosition() + this.rightEncoder.getPosition()) / 2.0D;
        if (avg_dist < dist) {
            Drivetrain.getInstance().tankDrive(power, power);
        } else if (avg_dist > dist) {
            Drivetrain.getInstance().tankDrive(-power, -power);
        }
    }

    @Override
    protected boolean isFinished() {
        double avg_dist = (this.leftEncoder.getPosition() + this.rightEncoder.getPosition()) / 2.0D;
        return avg_dist >= (this.dist * 0.99D) && avg_dist <= (this.dist * 1.01D);
    }
}
