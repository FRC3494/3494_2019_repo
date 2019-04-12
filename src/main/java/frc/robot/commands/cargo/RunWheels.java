package frc.robot.commands.cargo;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CargoManipulator;

public class RunWheels extends Command {

    public RunWheels() {
        requires(CargoManipulator.getInstance());
    }

    @Override
    protected void execute() {
        CargoManipulator.getInstance().runWheels((OI.getInstance().getXboxLeftTrigger() - OI.getInstance().getXboxRightTrigger()) * 0.65D);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
