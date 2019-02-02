package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

/**
 * Command to shift the climber in or out.
 */
public class Shift extends Command {

    private boolean value;

    public Shift(boolean engaged) {
        requires(Climber.getInstance());
        this.value = engaged;
    }

    @Override
    protected void execute() {
        Climber.getInstance().setShifter(this.value);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
