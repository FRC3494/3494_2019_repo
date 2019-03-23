package frc.robot.commands.spade;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.SpadeHatcher;

public class EjectHatch extends Command {

    public EjectHatch() {
        requires(SpadeHatcher.getInstance());
    }

    @Override
    protected void execute() {
        SpadeHatcher.getInstance().setEars(DoubleSolenoid.Value.kReverse);
        SpadeHatcher.getInstance().setEjectors(true);
        Timer.delay(0.1);
        SpadeHatcher.getInstance().setEjectors(false);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
