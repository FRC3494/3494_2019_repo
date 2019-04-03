package frc.robot.commands.drive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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

    public static double powerCurve(double x) {
        // https://www.desmos.com/calculator/g07ukjj7bl
        double curve = (0.5D * (Math.atan(Math.PI * (Math.abs(x) - 0.5D)))) + 0.5D;
        return Math.copySign(curve, x);
    }

    public static void normalize(double[] array) {
        double max = Math.abs(array[0]);
        boolean normFlag = max > 1;

        for (int i = 1; i < array.length; i++) {
            if (Math.abs(array[i]) > max) {
                max = Math.abs(array[i]);
                normFlag = max > 1;
            }
        }

        if (normFlag) {
            for (int i = 0; i < array.length; i++) {
                array[i] /= max;
            }
        }
    }

    private void correctForPitch(double[] stickSpeeds) {//x-tip
        //if its over 45 there's no point in correcting it
        if (Math.abs(pitchDegrees) < 45) {
            //correctionFactor keeps the tilt correction within a certain threshold so it doesn't correct too much
            double correctionFactor = (RobotMap.DRIVE.MAX_CORRECTION_FACTOR - RobotMap.DRIVE.MIN_CORRECTION_FACTOR) / (45 - RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES);

            double correctionOffset = correctionFactor * (pitchDegrees - RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES);
            stickSpeeds[0] += correctionOffset;
            stickSpeeds[1] += correctionOffset;
            normalize(stickSpeeds);
        }
    }

    private void updatePitchStatus() {
        this.pitchDegrees = NavX.getInstance().getPitchDegrees();
    }

    private void displayTippiness(){
        boolean forwardProblem = NavX.getInstance().getPitchDegrees() > RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES;
        boolean backwardProblem = NavX.getInstance().getPitchDegrees() < -RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES;

        SmartDashboard.putBoolean("Tipping Forward", forwardProblem);
        SmartDashboard.putBoolean("Tipping Backward", backwardProblem);
    }

    @Override
    protected void initialize() {
        setCamera("RPI");
    }

    @Override
    protected void execute() {
        double stickSpeeds[] = {powerCurve(OI.getInstance().getLeftY()), powerCurve(OI.getInstance().getRightY())};
        //double leftStick = RobotMath.powerCurve(OI.getInstance().getLeftY());
        //double rightStick = RobotMath.powerCurve(OI.getInstance().getRightY());

        updatePitchStatus();
        if(!Drivetrain.getInstance().getIsAntiTipDisabled()) {
            if (NavX.getInstance().getPitchDegrees() > RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES) {
                this.correctForPitch(stickSpeeds);
            }
        }
        this.displayTippiness();

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
