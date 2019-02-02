package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem {

    private Solenoid shifter;

    private static Climber INSTANCE = new Climber();

    private Climber() {
        this.shifter = new Solenoid(RobotMap.CLIMBER.SHIFTER_CHANNEL);
    }

    public void setShifter(boolean engaged) {
        this.shifter.set(engaged);
    }

    public boolean isEngaged() {
        return this.shifter.get();
    }

    public static Climber getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
