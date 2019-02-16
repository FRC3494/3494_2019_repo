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
        if (lBump) {
            CargoManipulatorArm.getInstance().setBrake(true);
            CargoManipulatorArm.getInstance().lift(0.75);
        } else if (rBump) {
            CargoManipulatorArm.getInstance().setBrake(true);
            CargoManipulatorArm.getInstance().lift(-0.20);
        } else {
            CargoManipulatorArm.getInstance().lift(0);
            CargoManipulatorArm.getInstance().setBrake(false);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
