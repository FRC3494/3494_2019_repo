package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Potentiometer;
import frc.robot.RobotMap;
import frc.robot.commands.arm.TwistArm;

/**
 * Arm subsystem. In the context of this class, "rotations" refers to rotations of the arm gearbox output shaft.
 */
public class CargoManipulatorArm extends PIDSubsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final CargoManipulatorArm INSTANCE = new CargoManipulatorArm();
    private TalonSRX armMotor;
    private DoubleSolenoid diskBrake;

    private AnalogInput ai;
    private Potentiometer pot;

    private double pidOut;

    private CargoManipulatorArm() {
        super(0.5, 0, 0);
        this.setInputRange(-90, 90);
        this.setOutputRange(-0.75, 0.75);
        this.getPIDController().setContinuous(false);
        this.setAbsoluteTolerance(3.5);

        armMotor = new TalonSRX(RobotMap.CARGO_ARM.ARM_MOTOR_CHANNEL);

        diskBrake = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CARGO_ARM.DISK_BRAKE_FORWARD, RobotMap.CARGO_ARM.DISK_BRAKE_REVERSE);
        diskBrake.set(DoubleSolenoid.Value.kReverse);

        ai = new AnalogInput(RobotMap.CARGO_ARM.POTENTIOMETER);
        pot = new AnalogPotentiometer(ai, 270, 0);
    }

    /**
     * Runs the arm motor at the specified power.
     *
     * @param power The power to run the lift at. Should be a {@code double}
     *              between -1 and 1.
     */
    public void lift(double power) {
        armMotor.set(ControlMode.PercentOutput, power * 0.5);
    }

    public void setBrake(boolean brake) {
        diskBrake.set(brake ? DoubleSolenoid.Value.kReverse : DoubleSolenoid.Value.kForward);
    }

    public double getPotVoltage() {
        return ai.getVoltage();
    }

    public static CargoManipulatorArm getInstance() {
        return INSTANCE;
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        setDefaultCommand(new TwistArm());
    }

    @Override
    protected double returnPIDInput() {
        // return ((-90.0 / 106.0) * this.pot.get()) + (13320.0 / 106.0);
        return (-90.0 / 37.8) * (this.pot.get() - 38);
    }

    @Override
    protected void usePIDOutput(double output) {
        this.pidOut = -output;
    }

    public double getPidOut() {
        return pidOut;
    }
}

