package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Combination cargo/hatch manipulator.
 */
public class ComboManipulator extends Subsystem {

    private TalonSRX leftMotor;
    private TalonSRX rightMotor;

    private Solenoid pistons;

    private static final ComboManipulator INSTANCE = new ComboManipulator();

    private ComboManipulator() {
    }

    public static ComboManipulator getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
    }
}
