package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.CAN;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;


public class CargoManipulator extends Subsystem {
    private static final CargoManipulator INSTANCE = new CargoManipulator();
    public static CargoManipulator getInstance(){
        return INSTANCE;
    }

    private CANSparkMax rightMotor;
    private CANSparkMax leftMotor;

    private CargoManipulator() {

        this.rightMotor = new CANSparkMax(RobotMap.CARGO_MANIPULATOR.RIGHT_MOTOR_CHANNEL, CANSparkMax.MotorType.kBrushless);
        this.leftMotor= new CANSparkMax(RobotMap.CARGO_MANIPULATOR.LEFT_MOTOR_CHANNEL, CANSparkMax.MotorType.kBrushless);

    }
    public void drive(double speed){
        this.leftMotor.set(speed);
        this.rightMotor.set(speed);
    }

    public void stop(){
        this.drive(0);
    }


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

}

