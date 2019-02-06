package frc.robot.commands.hatch;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.HatchManipulator;


public class ExtendHatchManipulator extends Command {
    public ExtendHatchManipulator() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(HatchManipulator.getInstance());
    }


    @Override
    protected void initialize()
    {
        HatchManipulator.getInstance().extend();
    }


    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return true;
    }


    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
