package frc.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.Drivetrain;


public class InitCurveDrive extends Command {
    private double initialTurn;
    private Timer timer;

    public InitCurveDrive() {
        requires(Drivetrain.getInstance());
        this.timer = new Timer();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    }

    private void setInitialTurn() {
        this.initialTurn = Robot.getLimelight().getXAngle() * RobotMap.DRIVETRAIN.AUTO_DRIVE_TURNING_MULTIPLIER;
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        this.setInitialTurn();
        Drivetrain.getInstance().setSetpoint(NavX.getInstance().getFusedHeading() + this.initialTurn);
        this.timer.start();
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double requestedRotationAmount = Drivetrain.getInstance().getPIDOutput();
        Drivetrain.getInstance().tankDrive(requestedRotationAmount, -requestedRotationAmount);
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
        return (Drivetrain.getInstance().onTarget() || timer.get() < 5;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {
        Drivetrain.getInstance().stop();
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
    }
}
