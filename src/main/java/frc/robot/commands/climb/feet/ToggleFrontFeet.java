package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ToggleFrontFeet extends Command {


    public ToggleFrontFeet() {
        requires(Climber.getInstance());
    }

    @Override
    protected void execute() {
        if (OI.getInstance().climberSafetyOff()) {
            if (Climber.getInstance().getFrontFoot().equals(DoubleSolenoid.Value.kForward)) {
                Climber.getInstance().setFrontFoot(DoubleSolenoid.Value.kReverse);
            } else {
                Climber.getInstance().setFrontFoot(DoubleSolenoid.Value.kForward);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
