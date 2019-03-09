package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        double left = OI.getInstance().getLeftY();
        double right = OI.getInstance().getRightY();

        if (!Climber.getInstance().isEngaged()) {
            Drivetrain.getInstance().tankDrive(powerCurve(left), powerCurve(right));
        } else {
            Drivetrain.getInstance().tankDrive(powerCurve(Math.abs(left)), powerCurve(Math.abs(left)));
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private static double powerCurve(double x) {
        // https://www.desmos.com/calculator/g07ukjj7bl
        double curve = (0.5D * (Math.atan(Math.PI * (x - 0.5D)))) + 0.5D;
        return Math.copySign(curve, x);
    }
}
