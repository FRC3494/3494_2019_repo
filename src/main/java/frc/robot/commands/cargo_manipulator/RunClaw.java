package frc.robot.commands.cargo_manipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.subsystems.CargoManipulator;

public class RunClaw extends Command {

    public RunClaw() {
        requires(CargoManipulator.getInstance());
    }

    @Override
    protected void execute() {
        if (OI.getInstance().getXboxA()) {
            CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.INTAKE_SPEED);
        } else if (OI.getInstance().getXboxB()) {
            CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.OUTAKE_SPEED);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
