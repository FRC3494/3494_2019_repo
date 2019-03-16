package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.RobotMap;
import frc.robot.commands.arm.TwistArm;

/**
 * Arm subsystem. In the context of this class, "rotations" refers to rotations of the arm gearbox output shaft.
 */
public class CargoManipulatorArm extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final CargoManipulatorArm INSTANCE = new CargoManipulatorArm();
    private TalonSRX armMotor;
    private DoubleSolenoid diskBrake;

    private Potentiometer pot;

    private CargoManipulatorArm() {
        armMotor = new TalonSRX(RobotMap.CARGO_ARM.ARM_MOTOR_CHANNEL);

        diskBrake = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CARGO_ARM.DISK_BRAKE_FORWARD, RobotMap.CARGO_ARM.DISK_BRAKE_REVERSE);
        diskBrake.set(DoubleSolenoid.Value.kForward);

        pot = new AnalogPotentiometer(RobotMap.CARGO_ARM.POTENTIOMETER, 270, 0);
    }

    /**
     * Runs the arm motor at the specified power.
     *
     * @param power The power to run the lift at. Should be a {@code double}
     *              between -1 and 1.
     */
    public void lift(double power) {
        armMotor.set(ControlMode.PercentOutput, power);
    }

    public void setBrake(boolean brake) {
        diskBrake.set(brake ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public static CargoManipulatorArm getInstance() {
        return INSTANCE;
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        setDefaultCommand(new TwistArm());
    }
}

