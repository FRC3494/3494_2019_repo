package frc.robot.subsystems;


import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

public class PDP extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final PDP INSTANCE = new PDP();
    private PowerDistributionPanel powerDistributionPanel;

    private PDP() {
        powerDistributionPanel = new PowerDistributionPanel();
    }

    public static PDP getInstance() {
        return INSTANCE;
    }

    public double getVoltage() {
        return this.powerDistributionPanel.getVoltage();
    }

    public double getCurrent(int channel) {
        return this.powerDistributionPanel.getCurrent(channel);
    }

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }
}