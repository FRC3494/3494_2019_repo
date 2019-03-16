package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.cargo.RunWheels;

/**
 * Cargo manipulator.
 */
public class CargoManipulator extends Subsystem {

    private TalonSRX roller;

    private static final CargoManipulator INSTANCE = new CargoManipulator();

    private CargoManipulator() {
        this.roller = new TalonSRX(RobotMap.CARGO_MANIPULATOR.ROLLER);
    }

    public void runWheels(double power) {
        this.roller.set(ControlMode.PercentOutput, power);
    }

    public static CargoManipulator getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new RunWheels());
    }
}
