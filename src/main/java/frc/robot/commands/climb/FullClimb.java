package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.RobotMap;
import frc.robot.commands.auto.drive.DistanceDrive;


public class FullClimb extends CommandGroup {

    public FullClimb() {
        addSequential(new Shift(DoubleSolenoid.Value.kReverse));
        addSequential(new DistanceDrive(RobotMap.CLIMBER.CLIMB_DISTANCE_INITIAL));
        addSequential(new Shift(DoubleSolenoid.Value.kForward));
        addSequential(new DistanceDrive(RobotMap.CLIMBER.CLIMB_DISTANCE_FINAL));
    }
}
