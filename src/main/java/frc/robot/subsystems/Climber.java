package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem {

    private DoubleSolenoid shifter;

    private static Climber INSTANCE = new Climber();

    private Climber() {
        this.shifter = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CLIMBER.SHIFTER_FORWARD_CHANNEL, RobotMap.CLIMBER.SHIFTER_REVERSE_CHANNEL);
    }

    public void setShifter(DoubleSolenoid.Value value) {
        this.shifter.set(value);
    }

    public boolean isEngaged() {
        return this.shifter.get().equals(DoubleSolenoid.Value.kReverse);
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
