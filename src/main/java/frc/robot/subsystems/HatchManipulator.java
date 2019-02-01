package frc.robot.subsystems;


import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class HatchManipulator extends Subsystem {
    private static final HatchManipulator INSTANCE = new HatchManipulator();
    private Solenoid PushHatchSolenoid1 = new Solenoid(1);
    private Solenoid PushHatchSolenoid2 = new Solenoid(2);
    // Put methods for controlling this subsystem
    // here. Call these from Commands.

    public static HatchManipulator getInstance(){
        return INSTANCE;
    }

    private HatchManipulator()
    {
        PushHatchSolenoid1.set(false);
        PushHatchSolenoid2.set(false);
    }

    public void extend()
    {
        PushHatchSolenoid1.set(true);
        PushHatchSolenoid2.set(true);
    }

    public void retract()
    {
        PushHatchSolenoid1.set(false);
        PushHatchSolenoid2.set(false);
    }


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

}

