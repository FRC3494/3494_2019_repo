package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;


public class ArcDrive extends TimedCommand {
    private Timer timer;
    private double arcRadius;
    private double initialAngleToTargetCenter;
    private double initialAngleToTargetEdge;
    private double initialDistToTargetCenter;
    private double initialDistToTargetEdge;
    private double angleBetweenCenterAndEdge;
    private double kiteLegLength;
    private double cornerToTargetDistance;
    private double initialDrivingDistance;
    private double angleToCenterRobotToTarget;

    public ArcDrive() {
        super(RobotMap.ARC_DRIVE.TIMEOUT);

        requires(Drivetrain.getInstance());

        this.timer = new Timer();
    }


    private void setInitialDrivingDistance(){
        this.initialDrivingDistance = (this.initialDistToTargetEdge * Math.cos(RobotMap.LIMELIGHT.FOV_RAD / 2)
            - this.initialDistToTargetEdge * Math.sin(RobotMap.LIMELIGHT.FOV_RAD / 2)) / Math.tan(RobotMap.LIMELIGHT.FOV_RAD / 2);
    }

    private void setKiteLegLength(){

    }

    private boolean isPathPossible(){
        return (this.cornerToTargetDistance > this.kiteLegLength);
    }

    private double setInitialDistanceToTargetCenter(){
        return 0;
    }

    private void setInitialAngleToTargetEdge(){
        this.initialDistToTargetEdge = this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge)
                + Math.sqrt(Math.pow(RobotMap.ARC_DRIVE.CARGO_HATCH_TAPE_WIDTH_FEET, 2) / 4
                - Math.pow(this.initialDistToTargetCenter, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge), 2));
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        this.timer.start();
        this.initialAngleToTargetCenter = Robot.getFrontLimelightInstance().getXDistance();
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {

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
        return false;
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
