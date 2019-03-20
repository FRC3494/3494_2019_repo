package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CargoManipulatorArm;

public class GotoPosition extends Command {

    private double angle;

    public GotoPosition(double angle) {
        this.angle = angle;
    }

    @Override
    protected void initialize() {
        CargoManipulatorArm.getInstance().enable();
        CargoManipulatorArm.getInstance().setSetpoint(angle);
        CargoManipulatorArm.getInstance().setBrake(false);
    }

    @Override
    protected void execute() {
        CargoManipulatorArm.getInstance().lift(CargoManipulatorArm.getInstance().getPidOut());
    }

    @Override
    protected boolean isFinished() {
        return CargoManipulatorArm.getInstance().onTarget();
    }

    @Override
    protected void end() {
        CargoManipulatorArm.getInstance().disable();
        CargoManipulatorArm.getInstance().setBrake(true);
    }
}
