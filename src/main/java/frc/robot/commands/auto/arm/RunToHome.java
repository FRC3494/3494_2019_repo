package frc.robot.commands.auto.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CargoManipulatorArm;

public class RunToHome extends Command {

    public RunToHome() {
        requires(CargoManipulatorArm.getInstance());
    }

    @Override
    protected void execute() {
        CargoManipulatorArm.getInstance().lift(-0.125);
    }

    @Override
    protected boolean isFinished() {
        return CargoManipulatorArm.getInstance().lineBroken();
    }

    @Override
    protected void end() {
        // just in case
        CargoManipulatorArm.getInstance().resetEncoder();
    }
}
