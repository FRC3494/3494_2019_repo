package frc.robot.commands.cargo_manipulator;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.CargoManipulator;


public class IntakeCargo extends Command {

    public IntakeCargo() {
        requires(CargoManipulator.getInstance());
    }


    @Override
    protected void initialize()
    {
        CargoManipulator.getInstance().stop();
    }



    @Override
    protected void execute()
    {
        CargoManipulator.getInstance().drive(RobotMap.CARGO_MANIPULATOR.INTAKE_SPEED);
    }



    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return false;
    }


    @Override
    protected void end() {
        CargoManipulator.getInstance().stop();
    }


    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
