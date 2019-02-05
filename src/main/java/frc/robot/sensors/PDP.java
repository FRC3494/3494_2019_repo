package frc.robot.sensors;


import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class PDP {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static final PDP INSTANCE = new PDP();
    private PowerDistributionPanel powerDistributionPanel;

    private PDP() {
        this.powerDistributionPanel = new PowerDistributionPanel();
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
}