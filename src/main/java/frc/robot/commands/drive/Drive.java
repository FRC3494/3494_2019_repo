package frc.robot.commands.drive;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.OI;
import frc.robot.RobotMap;
import frc.robot.sensors.NavX;
import frc.robot.sensors.PDP;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.RobotMath;

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

    private void correctForPitch(double[] stickSpeeds) {//x-tip
        //if its over 45 there's no point in correcting it
        if (Math.abs(pitchDegrees) < 45) {
            //correctionFactor keeps the tilt correction within a certain threshold so it doesn't correct too much
            double correctionFactor = (RobotMap.DRIVE.MAX_CORRECTION_FACTOR - RobotMap.DRIVE.MIN_CORRECTION_FACTOR) / (45 - RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES);

            double correctionOffset = correctionFactor * (pitchDegrees - RobotMap.DRIVE.PITCH_THRESHOLD_DEGREES);
            stickSpeeds[0] += correctionOffset;
            stickSpeeds[1] += correctionOffset;
            RobotMath.normalize(stickSpeeds);
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
        double stickSpeeds[] = {RobotMath.powerCurve(OI.getInstance().getLeftY()), RobotMath.powerCurve(OI.getInstance().getRightY())};
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
