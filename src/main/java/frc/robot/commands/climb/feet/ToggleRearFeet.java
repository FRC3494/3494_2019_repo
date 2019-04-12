package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class ToggleRearFeet extends Command {

    public ToggleRearFeet() {
        requires(Climber.getInstance());
    }

    @Override
    protected void initialize() {
        if (OI.getInstance().climberSafetyOff()) {
            if (Climber.getInstance().getRearFeet().equals(DoubleSolenoid.Value.kForward)) {
                Climber.getInstance().setRearFeet(DoubleSolenoid.Value.kReverse);
            } else {
                Climber.getInstance().setRearFeet(DoubleSolenoid.Value.kForward);
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
