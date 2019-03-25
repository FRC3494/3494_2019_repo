package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;

public class ButtonBoard extends GenericHID {
    public ButtonBoard(int port) {
        super(port);
    }

    @Override
    public double getX(Hand hand) {
        return 0;
    }

    @Override
    public double getY(Hand hand) {
        return 0;
    }
}
