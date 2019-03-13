package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.cargo.RunWheels;

/**
 * Combination cargo/hatch manipulator.
 */
public class CargoManipulator extends Subsystem {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;

    private Solenoid pistons;

    private static final CargoManipulator INSTANCE = new CargoManipulator();

    private CargoManipulator() {
        this.leftMotor = new TalonSRX(RobotMap.CARGO_MANIPULATOR.LEFT_MOTOR_CHANNEL);
        this.rightMotor = new TalonSRX(RobotMap.CARGO_MANIPULATOR.RIGHT_MOTOR_CHANNEL);

        this.pistons = new Solenoid(RobotMap.PCM_B, RobotMap.CARGO_MANIPULATOR.PISTONS_CHANNEL);
        this.pistons.set(false);
    }

    public void runWheels(double power) {
        this.leftMotor.set(ControlMode.PercentOutput, power);
        this.rightMotor.set(ControlMode.PercentOutput, -power);
    }

    public void setOpen(boolean open) {
        this.pistons.set(!open);
    }

    public boolean isOpen() {
        return !this.pistons.get();
    }

    public void toggleOpen() {
        this.setOpen(!this.isOpen());
    }

    public static CargoManipulator getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunWheels());
    }
}
