package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchManipulator extends Subsystem {
    private static final HatchManipulator INSTANCE = new HatchManipulator();
    private DoubleSolenoid pusher;
    private Solenoid centerRod;
    private DoubleSolenoid extender;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public static HatchManipulator getInstance() {
        return INSTANCE;
    }

    private HatchManipulator() {
        this.pusher = new DoubleSolenoid(RobotMap.PCM_B, RobotMap.HATCH_MANIPULATOR.PUSH_FORWARD_CHANNEL, RobotMap.HATCH_MANIPULATOR.PUSH_REVERSE_CHANNEL);
        this.pusher.set(DoubleSolenoid.Value.kForward);

        this.centerRod = new Solenoid(RobotMap.PCM_B, RobotMap.HATCH_MANIPULATOR.CENTER_FORWARD_CHANNEL);
        this.centerRod.set(false);

        extender = new DoubleSolenoid(RobotMap.PCM_B, RobotMap.HATCH_MANIPULATOR.EXTENDER_FORWARD, RobotMap.HATCH_MANIPULATOR.EXTENDER_REVERSE);
    }

    public void ejectHatch() {
        this.pusher.set(DoubleSolenoid.Value.kReverse);
    }

    public void retractPusher() {
        this.pusher.set(DoubleSolenoid.Value.kForward);
    }

    public void toggleCenter() {
        this.setCenterRod(!this.centerRod.get());
    }

    public void setCenterRod(boolean b) {
        this.centerRod.set(b);
    }

    public void setExtended(DoubleSolenoid.Value value) {
        this.extender.set(value);
    }

    public boolean isExtended() {
        return this.extender.get().equals(DoubleSolenoid.Value.kForward);
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

