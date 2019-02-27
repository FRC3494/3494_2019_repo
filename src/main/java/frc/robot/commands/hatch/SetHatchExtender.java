package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.HatchManipulator;

public class SetHatchExtender extends Command {

    private boolean extended;

    public SetHatchExtender(boolean e) {
        requires(HatchManipulator.getInstance());
        this.extended = e;
    }

    @Override
    protected void execute() {
        HatchManipulator.getInstance().setExtended(extended ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
