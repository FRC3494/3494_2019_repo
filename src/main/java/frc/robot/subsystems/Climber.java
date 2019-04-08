package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class Climber extends Subsystem {

    private DoubleSolenoid frontFoot;
    private DoubleSolenoid rearFeet;

    private TalonSRX winchLeftMaster;
    private VictorSPX winchLeftFollower;
    private TalonSRX winchRightMaster;
    private VictorSPX winchRightFollower;

    private static Climber INSTANCE = new Climber();

    private Climber() {
        this.frontFoot = new DoubleSolenoid(RobotMap.PCM_B, RobotMap.CLIMBER.FRONT_FOOT_FORWARD, RobotMap.CLIMBER.FRONT_FOOT_REVERSE);
        this.frontFoot.set(DoubleSolenoid.Value.kForward);
        this.rearFeet = new DoubleSolenoid(RobotMap.PCM_A, RobotMap.CLIMBER.REAR_FEET_FORWARD, RobotMap.CLIMBER.REAR_FEET_REVERSE);
        this.rearFeet.set(DoubleSolenoid.Value.kReverse);

        this.winchLeftMaster = new TalonSRX(RobotMap.CLIMBER.WINCH_LEFT_MASTER_CHANNEL);
        this.winchLeftMaster.setInverted(true);

        this.winchLeftFollower = new VictorSPX(RobotMap.CLIMBER.WINCH_LEFT_FOLLOWER_CHANNEL);
        this.winchLeftFollower.setInverted(true);
        this.winchLeftFollower.follow(this.winchLeftMaster);

        this.winchRightMaster = new TalonSRX(RobotMap.CLIMBER.WINCH_RIGHT_MASTER_CHANNEL);

        this.winchRightFollower = new VictorSPX(RobotMap.CLIMBER.WINCH_RIGHT_FOLLOWER_CHANNEL);
        this.winchRightFollower.follow(this.winchRightMaster);
    }

    public void setFrontFoot(DoubleSolenoid.Value value) {
        this.frontFoot.set(value);
    }

    public void setRearFeet(DoubleSolenoid.Value value) {
        this.rearFeet.set(value);
    }

    public void setWinchLeftMaster(double power) {
        this.winchLeftMaster.set(ControlMode.PercentOutput, power);
    }

    public void setWinchRightMaster(double power) {
        this.winchRightMaster.set(ControlMode.PercentOutput, power);
    }

    public DoubleSolenoid.Value getFrontFoot() {
        return this.frontFoot.get();
    }

    public DoubleSolenoid.Value getRearFeet() {
        return this.rearFeet.get();
    }

    public static Climber getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initDefaultCommand() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
