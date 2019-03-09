package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.arm.TwistArm;

public class CargoManipulatorArm extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final CargoManipulatorArm INSTANCE = new CargoManipulatorArm();
    private TalonSRX armMotor;
    private Solenoid diskBrake;

    private CargoManipulatorArm() {
        armMotor = new TalonSRX(RobotMap.CARGO_ARM.ARM_MOTOR_CHANNEL);
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative);

        diskBrake = new Solenoid(RobotMap.PCM_A, RobotMap.CARGO_ARM.DISK_BRAKE);
        diskBrake.set(true);
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

    public int getCounts() {
        return armMotor.getSelectedSensorPosition();
    }

    public double getEncoderPos() {
        return this.getCounts() * 0.25D;
    }

    public double getRotations() {
        return this.getEncoderPos() * RobotMap.CARGO_ARM.PPR;
    }

    public void resetEncoder() {
        armMotor.setSelectedSensorPosition(0);
    }

    public void setBrake(boolean brake) {
        diskBrake.set(!brake);
    }

    public static CargoManipulatorArm getInstance() {
        return INSTANCE;
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        setDefaultCommand(new TwistArm());
    }
}

