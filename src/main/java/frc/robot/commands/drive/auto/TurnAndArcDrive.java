package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;


public class TurnAndArcDrive extends CommandGroup {
    public boolean flag;

    public TurnAndArcDrive(double angleToTurnDeg) {
        addSequential(new TurnDrive(angleToTurnDeg));
        addSequential(new ArcDrive());
    }
}
