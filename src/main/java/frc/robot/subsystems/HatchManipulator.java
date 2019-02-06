package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class HatchManipulator extends Subsystem {
    private static final HatchManipulator INSTANCE = new HatchManipulator();
    private DoubleSolenoid pusher;
    private DoubleSolenoid centerRod;
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public static HatchManipulator getInstance() {
        return INSTANCE;
    }

    private HatchManipulator() {
        this.pusher = new DoubleSolenoid(RobotMap.HATCH_MANIPULATOR.PUSH_FORWARD_CHANNEL, RobotMap.HATCH_MANIPULATOR.PUSH_REVERSE_CHANNEL);
        this.pusher.set(DoubleSolenoid.Value.kReverse);

        this.centerRod = new DoubleSolenoid(RobotMap.HATCH_MANIPULATOR.CENTER_FORWARD_CHANNEL, RobotMap.HATCH_MANIPULATOR.CENTER_REVERSE_CHANNEL);
        this.centerRod.set(DoubleSolenoid.Value.kReverse);
    }

    public void ejectHatch() {
        this.pusher.set(DoubleSolenoid.Value.kForward);
    }

    public void retractPusher() {
        this.pusher.set(DoubleSolenoid.Value.kReverse);
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

