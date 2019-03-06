package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CargoManipulatorArm;

public class TwistArm extends Command {

    public TwistArm() {
        requires(CargoManipulatorArm.getInstance());
    }

    @Override
    protected void execute() {
        double x = OI.getInstance().getXboxRightY();
        if (x != 0) {
            CargoManipulatorArm.getInstance().lift(x);
        } else {
            CargoManipulatorArm.getInstance().lift(0);
            CargoManipulatorArm.getInstance().setBrake(true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
