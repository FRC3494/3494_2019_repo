package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.combo_manipulator.RunWheels;

/**
 * Combination cargo/hatch manipulator.
 */
public class ComboManipulator extends Subsystem {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;

    private Solenoid pistons;

    private static final ComboManipulator INSTANCE = new ComboManipulator();

    private ComboManipulator() {
        this.leftMotor = new TalonSRX(RobotMap.COMBO_MANIPULATOR.LEFT_MOTOR_CHANNEL);
        this.rightMotor = new TalonSRX(RobotMap.COMBO_MANIPULATOR.RIGHT_MOTOR_CHANNEL);

        this.pistons = new Solenoid(RobotMap.PCM_A, RobotMap.COMBO_MANIPULATOR.PISTONS_CHANNEL);
        this.pistons.set(false);
    }

    public void runWheels(double power) {
        this.leftMotor.set(ControlMode.PercentOutput, power);
        this.rightMotor.set(ControlMode.PercentOutput, -power);
    }

    public void setOpen(boolean open) {
        this.pistons.set(!open);
    }

    public static ComboManipulator getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunWheels());
    }
}
