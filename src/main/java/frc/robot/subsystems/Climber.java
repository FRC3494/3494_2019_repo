package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem {

    private DoubleSolenoid shifter;

    private DoubleSolenoid frontFoot;
    private DoubleSolenoid rearFeet;

    private static Climber INSTANCE = new Climber();

    private Climber() {
        this.shifter = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CLIMBER.SHIFTER_FORWARD_CHANNEL, RobotMap.CLIMBER.SHIFTER_REVERSE_CHANNEL);
        this.shifter.set(DoubleSolenoid.Value.kForward);

        this.frontFoot = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CLIMBER.FRONT_FOOT_FORWARD, RobotMap.CLIMBER.FRONT_FOOT_REVERSE);
        this.frontFoot.set(DoubleSolenoid.Value.kReverse);
        this.rearFeet = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CLIMBER.REAR_FEET_FORWARD, RobotMap.CLIMBER.REAR_FEET_REVERSE);
        this.rearFeet.set(DoubleSolenoid.Value.kForward);
    }

    public void setShifter(DoubleSolenoid.Value value) {
        this.shifter.set(value);
    }

    public void setFrontFoot(DoubleSolenoid.Value value) {
        this.frontFoot.set(value);
    }

    public void setRearFeet(DoubleSolenoid.Value value) {
        this.rearFeet.set(value);
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
