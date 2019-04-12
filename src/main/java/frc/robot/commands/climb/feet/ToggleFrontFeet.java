package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

public class ToggleFrontFeet extends Command {

    public ToggleFrontFeet() {
        requires(Climber.getInstance());
    }

    @Override
    protected void execute() {
        if (Climber.getInstance().getFrontFoot().equals(DoubleSolenoid.Value.kReverse)) {
            Climber.getInstance().setFrontFoot(DoubleSolenoid.Value.kForward);
        } else {
            Climber.getInstance().setFrontFoot(DoubleSolenoid.Value.kReverse);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}