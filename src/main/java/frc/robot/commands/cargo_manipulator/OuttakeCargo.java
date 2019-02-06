package frc.robot.commands.cargo_manipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.CargoManipulator;

public class OuttakeCargo extends Command {
    public OuttakeCargo() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(CargoManipulator.getInstance());
    }


    @Override
    protected void initialize() {
        CargoManipulator.getInstance().stop();
    }

    protected void execute() {
        CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.OUTAKE_SPEED);
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected void end() {
        CargoManipulator.getInstance().stop();
    }
}
