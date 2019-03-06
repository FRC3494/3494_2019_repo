package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;


public class ArcDrive extends TimedCommand {
    private Timer timer;
    private Vector2d directionIntoTarget;//not set yet
    private double arcRadius;
    private double initialAngleToTargetCenter;//not set yet
    private double initialAngleToTargetEdge;//not set yet
    private double initialDistToTargetCenter;//not set yet
    private double initialDistToTargetEdge;//not set yet
    private double angleBetweenCenterAndEdge;//not set yet
    private double kiteLegLength;
    private double cornerToTargetDist;
    private double initialDrivingDist;
    private double initialDistToCorner;
    private double angleToCenterRobotToTarget;//not set yet
    private double distToTargetAfterArc;//not set yet
    private double arcAngle;

    public ArcDrive() {
        super(RobotMap.ARC_DRIVE.TIMEOUT);

        requires(Drivetrain.getInstance());

        this.timer = new Timer();

        this.directionIntoTarget = new Vector2d(0, 0);
    }

    private boolean isRightOfTarget(){

    }

    private void setArcAngle(){
        this.arcAngle = Math.acos(1
                - (Math.pow(this.initialDistToTargetCenter*Math.cos(this.initialAngleToTargetCenter)
                    + this.directionIntoTarget.x * this.distToTargetAfterArc - this.initialDrivingDist, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)
                    + this.directionIntoTarget.y * this.distToTargetAfterArc, 2))
                / (2 * Math.pow(this.arcRadius, 2)));
    }

    //this.directionIntoTarget.rotate() takes degrees and rotates counterclockwise
    private Vector2d getRotatedDirectionIntoTarget(){
        Vector2d rotatedDirection = new Vector2d(this.directionIntoTarget.x, this.directionIntoTarget.y);
        rotatedDirection.rotate(90);
        return rotatedDirection;
    }

    private void setDistToTargetAfterArc(){
        this.distToTargetAfterArc = this.cornerToTargetDist - this.kiteLegLength;
    }

    private void setInitialDrivingDistance(){
        this.initialDrivingDist = (this.initialDistToTargetEdge * Math.cos(RobotMap.LIMELIGHT.FOV_RAD / 2)
            - this.initialDistToTargetEdge * Math.sin(RobotMap.LIMELIGHT.FOV_RAD / 2)) / Math.tan(RobotMap.LIMELIGHT.FOV_RAD / 2);
    }

    //equation: sqrt((d_ix - L_x)^2 + (d_iy)^2))
    private void setCornerToTargetDist(){
        this.cornerToTargetDist = Math.sqrt(Math.pow(
                this.initialDistToTargetCenter*Math.cos(this.initialAngleToTargetCenter) - this.initialDistToCorner, 2))
                + (Math.pow(this.initialDistToTargetCenter*Math.sin(this.initialAngleToTargetCenter),2));
    }

    private void setKiteLegLength(){
        this.kiteLegLength = this.initialDistToCorner - this.initialDrivingDist;
    }
    private void setInitialDistToCorner(){
        this.initialDistToCorner = -(this.initialDistToTargetCenter*Math.sin(this.initialAngleToTargetCenter)) / Math.tan(this.angleToCenterRobotToTarget)
                + (this.initialDistToTargetCenter*Math.cos(this.initialAngleToTargetCenter));
    }
    private boolean isPathPossible(){
        return (this.cornerToTargetDist > this.kiteLegLength);
    }

    private double setInitialDistanceToTargetCenter(){
        return 0;
    }

    private void setArcRadius(){
        Vector2d rotatedDirection = getRotatedDirectionIntoTarget();
        this.arcRadius = rotatedDirection.y/rotatedDirection.x * (this.initialDistToTargetCenter*Math.cos(this.initialAngleToTargetCenter)
            + this.directionIntoTarget.x * this.distToTargetAfterArc + this.initialDrivingDist)
            + this.initialDistToTargetCenter*Math.sin(this.initialAngleToTargetCenter)
            + this.directionIntoTarget.y * this.distToTargetAfterArc;
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
