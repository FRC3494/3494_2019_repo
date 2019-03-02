package frc.robot.commands.cargo_manipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CargoManipulator;

public class RunClaw extends Command {

    public RunClaw() {
        requires(CargoManipulator.getInstance());
    }

    @Override
    protected void execute() {
        CargoManipulator.getInstance().drive((OI.getInstance().getXboxLeftTrigger() / 2.0) - OI.getInstance().getXboxRightTrigger());
        // if (OI.getInstance().getXboxLeftBumper()) {
        //    CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.INTAKE_SPEED);
        // } else if (OI.getInstance().getXboxRightBumper()) {
        //     CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.OUTAKE_SPEED);
        // }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
