package frc.robot.commands.arm;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.CargoManipulatorArm;

public class TwistArm extends Command {

    public TwistArm() {
        requires(CargoManipulatorArm.getInstance());
    }

    @Override
    protected void execute() {
        double x = powerCurve(OI.getInstance().getXboxRightX());
        if (x != 0) {
            CargoManipulatorArm.getInstance().setBrake(false);
            CargoManipulatorArm.getInstance().lift(x);
        } else if (OI.getInstance().getXboxA()) {
            CargoManipulatorArm.getInstance().lift(0.3);
            CargoManipulatorArm.getInstance().setBrake(true);
        } else {
            CargoManipulatorArm.getInstance().lift(0);
            CargoManipulatorArm.getInstance().setBrake(true);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private static double powerCurve(double x) {
        // https://www.desmos.com/calculator/77lmnjh7c8
        double curve = -0.5 * (Math.cos(Math.abs(Math.PI * x))) + 0.5;
        return Math.copySign(curve, x);
    }
}
