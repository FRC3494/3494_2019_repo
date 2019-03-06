package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;

public class SetRearFeet extends Command {

    private DoubleSolenoid.Value v;

    public SetRearFeet(DoubleSolenoid.Value value) {
        requires(Climber.getInstance());
        v = value;
    }

    @Override
    protected void execute() {
        System.out.println(v.name());
        Climber.getInstance().setRearFeet(v);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
