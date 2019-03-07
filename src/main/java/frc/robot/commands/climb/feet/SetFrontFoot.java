package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

public class SetFrontFoot extends Command {

    private DoubleSolenoid.Value v;

    public SetFrontFoot(DoubleSolenoid.Value value) {
        requires(Climber.getInstance());
        v = value;
    }

    @Override
    protected void execute() {
        Climber.getInstance().setFrontFoot(v);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
