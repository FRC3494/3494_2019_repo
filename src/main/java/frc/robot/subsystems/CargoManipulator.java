package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.cargo_manipulator.RunClaw;

public class CargoManipulator extends Subsystem {
    private static final CargoManipulator INSTANCE = new CargoManipulator();

    public static CargoManipulator getInstance() {
        return INSTANCE;
    }

    private TalonSRX rightMotor;

    private CargoManipulator() {
        this.rightMotor = new TalonSRX(RobotMap.CARGO_MANIPULATOR.RIGHT_MOTOR_CHANNEL);
    }

    public void drive(double speed) {
        this.rightMotor.set(ControlMode.PercentOutput, speed);
    }

    public void stop() {
        this.drive(0);
    }

    public void initDefaultCommand() {
        setDefaultCommand(new RunClaw());
    }
}

