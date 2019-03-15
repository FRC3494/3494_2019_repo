package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class SpadeHatcher extends Subsystem {

    private DoubleSolenoid ears;

    private static SpadeHatcher INSTANCE = new SpadeHatcher();

    private SpadeHatcher() {
        this.ears = new DoubleSolenoid(RobotMap.PCM_B, RobotMap.SPADE.FORWARD_CHANNEL, RobotMap.SPADE.REVERSE_CHANNEL);
        this.ears.set(DoubleSolenoid.Value.kReverse);
    }


    public void setEars(DoubleSolenoid.Value value) {
        this.ears.set(value);
    }

    public DoubleSolenoid.Value getEars() {
        return this.ears.get();
    }

    public void toggle() {
        if (this.getEars().equals(DoubleSolenoid.Value.kForward)) {
            this.setEars(DoubleSolenoid.Value.kReverse);
        } else {
            this.setEars(DoubleSolenoid.Value.kForward);
        }
    }

    public static SpadeHatcher getInstance() {
        return INSTANCE;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    @Override
    protected void initDefaultCommand() {
    }
}
