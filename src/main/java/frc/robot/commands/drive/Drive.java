package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        double left = OI.getInstance().getLeftY();
        double right = OI.getInstance().getRightY();
        Drivetrain.getInstance().tankDrive(left, right);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
