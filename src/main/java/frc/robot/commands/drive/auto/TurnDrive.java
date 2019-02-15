package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.RobotMap;
import frc.robot.commands.drive.Drive;
import frc.robot.sensors.NavX;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.SynchronousPIDF;


public class TurnDrive extends TimedCommand {
    private SynchronousPIDF pidController;
    private Timer timer;
    private double lastTime;


    public TurnDrive(double setpoint) {
        super(RobotMap.TURN_DRIVE.TIMEOUT);
        requires(Drivetrain.getInstance());

        this.pidController = new SynchronousPIDF(RobotMap.TURN_DRIVE.kP,
                RobotMap.TURN_DRIVE.kI,
                RobotMap.TURN_DRIVE.kD,
                RobotMap.TURN_DRIVE.kF);

        this.pidController.setContinuous();
        this.pidController.setOutputRange(-1,1);
        this.pidController.setInputRange(0,360);
        this.pidController.setSetpoint((NavX.getInstance().getFusedHeading() + setpoint) % 360);
        this.timer = new Timer();
    }



    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        this.timer.start();
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute()
    {
        double output = this.pidController.calculate(NavX.getInstance().getFusedHeading(), timer.get()-this.lastTime);
        Drivetrain.getInstance().tankDrive(output, -output);
        this.lastTime = timer.get();
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
        return this.pidController.onTarget(RobotMap.TURN_DRIVE.TOLERANCE_RANGE_PID);
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
        super.interrupted();
    }
}
