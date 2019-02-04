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
        boolean lBump = OI.getInstance().getXboxLeftBumper();
        boolean rBump = OI.getInstance().getXboxRightBumper();
        if (rBump) {
            CargoManipulatorArm.getInstance().lift(0.5);
        } else if (lBump) {
            CargoManipulatorArm.getInstance().lift(-0.5);
        } else {
            CargoManipulatorArm.getInstance().lift(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
