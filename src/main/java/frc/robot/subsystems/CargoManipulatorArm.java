package frc.robot.subsystems;


import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;
import frc.robot.RobotMap;

public class CargoManipulatorArm extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
/*when you start on the arm don't forget that there's an encoder on it to help in positioning
for now i just need you to implement methods for getting the number of counts and such, and we can worry about "go to x position" commands later
the encoder is not integrated into the motor or anything so you'll need to make an actual Encoder object (or whatever WPI has potentially refactored it to)
*/
    private CargoManipulatorArm INSTANCE = new CargoManipulatorArm();
    private Encoder e;

    private CargoManipulatorArm(){
        e = new Encoder(RobotMap.CARGO_ARM.CHANNEL_ONE, RobotMap.CARGO_ARM.CHANNEL_TWO);
    }

    public int getCounts(){
        return e.get();
    }

    public double getDistance(){
        return e.getDistance();
    }

    public void resetEncoder(){
        e.reset();
    }

    public CargoManipulatorArm getInstance(){
        return this.INSTANCE;
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}

