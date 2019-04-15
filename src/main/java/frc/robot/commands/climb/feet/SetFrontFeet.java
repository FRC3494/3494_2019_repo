package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class SetFrontFeet extends Command {

    private DoubleSolenoid.Value v;

    public SetFrontFeet(DoubleSolenoid.Value value) {
        requires(Climber.getInstance());
        this.v = value;
    }

    @Override
    protected void initialize() {
        if (OI.getInstance().climberSafetyOff()) {
            Climber.getInstance().setFrontFoot(v);
        }
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
