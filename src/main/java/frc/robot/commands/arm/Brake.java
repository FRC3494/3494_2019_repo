package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.CargoManipulatorArm;

public class Brake extends Command {

    private boolean b;

    public Brake(boolean brake) {
        requires(CargoManipulatorArm.getInstance());
        this.b = brake;
    }

    @Override
    protected void execute() {
        CargoManipulatorArm.getInstance().setBrake(this.b);
    }

    @Override
    protected boolean isFinished() {
        return true;
    }
}
