package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class SpadeHatcher extends Subsystem {

    private DoubleSolenoid ears;
    private Solenoid ejectors;

    private static SpadeHatcher INSTANCE = new SpadeHatcher();

    private SpadeHatcher() {
        this.ears = new DoubleSolenoid(RobotMap.PCM_B, RobotMap.SPADE.FORWARD_CHANNEL, RobotMap.SPADE.REVERSE_CHANNEL);
        this.ears.set(DoubleSolenoid.Value.kReverse);

        this.ejectors = new Solenoid(RobotMap.PCM_A, RobotMap.SPADE.EJECTORS);
        this.ejectors.set(false);
    }


    public void setEars(DoubleSolenoid.Value value) {
        this.ears.set(value);
    }

    public DoubleSolenoid.Value getEars() {
        return this.ears.get();
    }

    public void setEjectors(boolean on) {
        this.ejectors.set(on);
    }

    public boolean getEjectors() {
        return this.ejectors.get();
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
