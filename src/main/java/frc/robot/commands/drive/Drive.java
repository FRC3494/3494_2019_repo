package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        double left = OI.getInstance().getLeftY();
        double right = OI.getInstance().getRightY();

        if (!Climber.getInstance().isEngaged()) {
            Drivetrain.getInstance().tankDrive(left, right);
        } else {
            Drivetrain.getInstance().tankDrive(Math.abs(left), Math.abs(left));
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
