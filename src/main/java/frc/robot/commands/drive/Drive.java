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
        double leftStick = OI.getInstance().getLeftY();
        double rightStick = OI.getInstance().getRightY();

        if (!Climber.getInstance().isEngaged()) {
            Drivetrain.getInstance().tankDrive(leftStick, rightStick);
        } else {
            Drivetrain.getInstance().tankDrive(Math.abs(leftStick), Math.abs(leftStick));
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
