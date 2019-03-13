package frc.robot.commands.combo_manipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.ComboManipulator;

public class RunWheels extends Command {

    public RunWheels() {
        requires(ComboManipulator.getInstance());
    }

    @Override
    protected void execute() {
        ComboManipulator.getInstance().runWheels((OI.getInstance().getXboxLeftTrigger() - OI.getInstance().getXboxRightTrigger()) / 2.0);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
