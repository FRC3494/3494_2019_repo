package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    /**
     * Indicator of which side of the robot is the "front" for operator driving. Set to true iff the driver has
     * swapped fronts. Use for applications beyond operator input is strictly forbidden for sanity.
     */
    private boolean sideFlipped = false;

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    @Override
    protected void execute() {
        double leftStick = powerCurve(OI.getInstance().getLeftY());
        double rightStick = powerCurve(OI.getInstance().getRightY());

        if (!Climber.getInstance().isEngaged()) {
            if (!sideFlipped) {
                Drivetrain.getInstance().tankDrive(leftStick, rightStick);
            } else {
                Drivetrain.getInstance().tankDrive(-rightStick, -leftStick);
            }
        } else {
            Drivetrain.getInstance().tankDrive(Math.abs(leftStick), Math.abs(leftStick));
        }

        int pov = OI.getInstance().getLeftPOV();
        if (pov != -1) {
            if (pov == 0) {
                this.sideFlipped = false;
            } else if (pov == 180) {
                this.sideFlipped = true;
            }
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private static double powerCurve(double x) {
        // https://www.desmos.com/calculator/g07ukjj7bl
        double curve = (0.5D * (Math.atan(Math.PI * (Math.abs(x) - 0.5D)))) + 0.5D;
        return Math.copySign(curve, x);
    }
}
