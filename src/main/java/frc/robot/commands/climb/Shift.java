package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

/**
 * Command to shift the climber in or out.
 */
public class Shift extends Command {

    private DoubleSolenoid.Value value;

    /**
     * Constructor. The direction is a {@link edu.wpi.first.wpilibj.DoubleSolenoid.Value}.
     * - {@link edu.wpi.first.wpilibj.DoubleSolenoid.Value#kForward} corresponds to disengaged.
     * - {@link edu.wpi.first.wpilibj.DoubleSolenoid.Value#kReverse} corresponds to engaged.
     *
     * @param direction The direction to shift in.
     */
    public Shift(DoubleSolenoid.Value direction) {
        requires(Climber.getInstance());
        this.value = direction;
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
