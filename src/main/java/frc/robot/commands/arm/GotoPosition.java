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
        if (CargoManipulatorArm.getInstance().getRotations() > this.rotations) {
            CargoManipulatorArm.getInstance().lift(-0.25);
        } else if (CargoManipulatorArm.getInstance().getRotations() < this.rotations) {
            CargoManipulatorArm.getInstance().lift(0.25);
        }
    }

    @Override
    protected boolean isFinished() {
        return Math.abs(CargoManipulatorArm.getInstance().getRotations() - rotations) < 0.01;
    }
}
