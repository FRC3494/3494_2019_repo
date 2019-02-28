package frc.robot.commands.climb.groups;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.commands.climb.Shift;
import frc.robot.commands.climb.feet.SetFrontFoot;
import frc.robot.commands.climb.feet.SetRearFeet;

public class LevelThree {
    public static class READY extends CommandGroup {
        public READY() {
            addSequential(new Shift(DoubleSolenoid.Value.kReverse));
            addSequential(new SetFrontFoot(DoubleSolenoid.Value.kForward));
            addSequential(new SetRearFeet(DoubleSolenoid.Value.kForward));
        }
    }

    public static class UNREADY extends CommandGroup {
        public UNREADY() {
            addSequential(new Shift(DoubleSolenoid.Value.kForward));
            addSequential(new SetFrontFoot(DoubleSolenoid.Value.kReverse));
            addSequential(new SetRearFeet(DoubleSolenoid.Value.kReverse));
        }
    }
}
