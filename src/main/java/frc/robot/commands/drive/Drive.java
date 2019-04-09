package frc.robot.commands.drive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.Drivetrain;

public class Drive extends Command {

    /**
     * Indicator of which side of the robot is the "front" for operator driving. Set to true iff the driver has
     * swapped fronts. Use for applications beyond operator input is strictly forbidden for sanity.
     */
    private boolean sideFlipped = false;

    private double pitchDegrees;

    public Drive() {
        requires(Drivetrain.getInstance());
    }

    private static double powerCurve(double x) {
        // https://www.desmos.com/calculator/g07ukjj7bl
        double curve = (0.5D * (Math.atan(Math.PI * (Math.abs(x) - 0.5D)))) + 0.5D;
        return Math.copySign(curve, x);
    }

    /**
     * @param motorSpeeds array of motor power values
     *                    If any of the values are more than 1, they aren't valid values for motor power.
     *                    If so, it divides all array values by the largest value to preserve the value ratios while making them valid motor power values.
     */
    private void normalize(double[] motorSpeeds) {
        double max = Math.abs(motorSpeeds[0]);
        boolean normFlag = max > 1;

        for (int i = 1; i < motorSpeeds.length; i++) {
            if (Math.abs(motorSpeeds[i]) > max) {
                max = Math.abs(motorSpeeds[i]);
                normFlag = max > 1;
            }
        }

        if (normFlag) {
            for (int i = 0; i < motorSpeeds.length; i++) {
                motorSpeeds[i] /= max;
            }
        }
    }

    private void correctForPitch(double[] stickSpeeds) {//x-tip
        //if its over 45 there's no point in correcting it
        if (Math.abs(pitchDegrees) < 45) {
            //correctionFactor keeps the tilt correction within a certain threshold so it doesn't correct too much

            double correctionOffset = RobotMap.DRIVE.CORRECTION_FACTOR * (pitchDegrees - RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES);
            stickSpeeds[0] += correctionOffset;
            stickSpeeds[1] += correctionOffset;
            normalize(stickSpeeds);
        }
    }

    private void updatePitchStatus() {
        this.pitchDegrees = NavX.getInstance().getPitchDegrees();
    }

    @Override
    protected void initialize() {
        setCamera("RPI");
    }

    @Override
    protected void execute() {
        double[] stickSpeeds = {powerCurve(OI.getInstance().getLeftY()), powerCurve(OI.getInstance().getRightY())};

        if (!Drivetrain.getInstance().getIsAntiTipDisabled()) {
            updatePitchStatus();
            if (pitchDegrees > RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES) {
                this.correctForPitch(stickSpeeds);
            }
        }

        if (!sideFlipped) {
            Drivetrain.getInstance().tankDrive(stickSpeeds[0], stickSpeeds[1]);
        } else {
            Drivetrain.getInstance().tankDrive(-stickSpeeds[1], -stickSpeeds[0]);
        }

        int pov = OI.getInstance().getLeftPOV();
        if (pov != -1) {
            if (pov == 0) {
                this.sideFlipped = false;
                setCamera("RPI");
            } else if (pov == 180) {
                this.sideFlipped = true;
                setCamera("USB");
            }
        }

    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    private static boolean setCamera(String camera) {
        NetworkTable engineering = NetworkTableInstance.getDefault().getTable("engineering");
        return engineering.getEntry("camera").setString(camera);
    }
}
