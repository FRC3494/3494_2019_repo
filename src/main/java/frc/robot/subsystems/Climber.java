package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem {

    private DoubleSolenoid shifterLeft;
    private DoubleSolenoid shifterRight;

    private static Climber INSTANCE = new Climber();

    private Climber() {
        this.shifterLeft = new DoubleSolenoid(RobotMap.CLIMBER.SHIFTER_FORWARD_CHANNEL_LEFT, RobotMap.CLIMBER.SHIFTER_REVERSE_CHANNEL_LEFT);
        this.shifterRight = new DoubleSolenoid(RobotMap.CLIMBER.SHIFTER_FORWARD_CHANNEL_RIGHT, RobotMap.CLIMBER.SHIFTER_REVERSE_CHANNEL_RIGHT);
    }

    public void setShifter(DoubleSolenoid.Value value) {
        this.shifterLeft.set(value);
        this.shifterRight.set(value);
    }

    public boolean isEngaged() {
        return this.shifterLeft.get().equals(DoubleSolenoid.Value.kReverse);
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
