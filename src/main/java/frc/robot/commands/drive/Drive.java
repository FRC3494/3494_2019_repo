package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
