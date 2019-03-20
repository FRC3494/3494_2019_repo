package frc.robot.commands.auto.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.Drivetrain;

public class ArcadeFollower extends Command {

    private double velo;

    public ArcadeFollower() {
        this(0.5);
    }

    public ArcadeFollower(double speed) {
        requires(Drivetrain.getInstance());
        velo = speed;
    }

    @Override
    protected void initialize() {
        // Enable PID controller, just in case
        Drivetrain.getInstance().enable();
    }

    @Override
    protected void execute() {
        Drivetrain.getInstance().setSetpoint(NavX.getInstance().getFusedHeading() + Robot.getLimelight().getTargetXAngleDeg());
        double tune = Drivetrain.getInstance().getPidOutput();
        Drivetrain.getInstance().tankDrive(velo + tune, velo - tune);
    }

    @Override
    protected boolean isFinished() {
        return (!OI.getInstance().cruiseControlCancel()) || Drivetrain.getInstance().getUltrasonicDistance() < 254.0;
    }
}
