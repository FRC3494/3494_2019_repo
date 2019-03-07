package frc.robot.commands.auto;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class SequentialAutoGroup extends CommandGroup {
    public SequentialAutoGroup(Command... commands) {
        for (Command c : commands) {
            addSequential(c);
        }
    }
}
