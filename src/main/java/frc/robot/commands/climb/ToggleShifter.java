package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

public class ToggleShifter extends Command {

    public ToggleShifter() {
        requires(Climber.getInstance());
    }

    @Override
    protected void execute() {
        if (Climber.getInstance().isEngaged()) {
            Climber.getInstance().setShifter(DoubleSolenoid.Value.kForward);
        } else {
            Climber.getInstance().setShifter(DoubleSolenoid.Value.kReverse);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
