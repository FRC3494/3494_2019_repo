package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CargoManipulatorArm;

public class GotoPosition extends Command {

    private double rotations;

    public GotoPosition(double rotations) {
        requires(CargoManipulatorArm.getInstance());
        this.rotations = rotations;
    }

    @Override
    protected void execute() {
        CargoManipulatorArm.getInstance().setLiftTarget(rotations);
    }

    @Override
    protected boolean isFinished() {
        return CargoManipulatorArm.getInstance().getLiftError() < 100;
    }
}
