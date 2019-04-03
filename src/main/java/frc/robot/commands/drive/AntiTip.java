package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.RobotMap;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.Drivetrain;


public class AntiTip extends Command {
    private boolean pitchProblem;
    private boolean rollProblem;

    private double leftSpeed;
    private double rightSpeed;

    private double pitchRadians;
    private double rollRadians;

    public AntiTip() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    private void updateThresholdStatus(){
        this.pitchRadians = NavX.getInstance().getPitchDegrees() * Math.PI / 180;
        this.rollRadians = NavX.getInstance().getRollDegrees() * Math.PI / 180;
        this.pitchProblem = (pitchRadians > RobotMap.DRIVETRAIN.PITCH_THRESHOLD_RADIANS);
        this.rollProblem = (rollRadians > RobotMap.DRIVETRAIN.ROLL_THRESHOLD_RADIANS);
    }

    private void changePitch(){//x-tip
        this.leftSpeed = ;
        this.rightSpeed = ;
        Drivetrain.getInstance().tankDrive(this.leftSpeed, this.rightSpeed);
    }

/** Team 263 example
 * // Correct tilt if abs(tilt) > tiltThresh
 * 		// Map to 10-40% output
 *
 * 		float pitch = mNavX.getPitch();
 * 		if (Math.abs(pitch) > Constants.kTiltThresh && Math.abs(pitch) < 45) {
 * 			double slope = (0.4 - 0.1) / (45 - Constants.kTiltThresh);
 * 			double correctionOffset = slope * (pitch - Constants.kTiltThresh);
 * 			double[] tmp = { leftStick + correctionOffset, rightStick + correctionOffset };
 * 			normalize(tmp);
 * 			leftStick = tmp[0];
 * 			rightStick = tmp[1];
 *                }
 */


    /**
     * Team 263 didn't use roll, may not be necessary?
    private void changeRoll(){//y-tip
        this.leftSpeed = ;
        this.rightSpeed = ;
    }
     */


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {

    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        updateThresholdStatus();
        changePitch();
    }


    /**
     * <p>
     * Returns whether this command is finished. If it is, then the command will be removed and
     * {@link #end()} will be called.
     * </p><p>
     * It may be useful for a team to reference the {@link #isTimedOut()}
     * method for time-sensitive commands.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. It is recommended to use
     * {@link edu.wpi.first.wpilibj.command.InstantCommand} (added in 2017) for this.
     * </p>
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return Drivetrain.getInstance().getIsAntiTipDisabled() || !pitchProblem;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {

    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
