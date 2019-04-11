package frc.robot.commands.climb;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.subsystems.Climber;


public class RunWinches extends Command {

    private double power;

    public RunWinches(double power) {
        requires(Climber.getInstance());
        this.power = power;
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        Climber.getInstance().setWinchLeftMaster(power);
        Climber.getInstance().setWinchRightMaster(power);
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
        return true;
    }

    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {
        Climber.getInstance().setWinchLeftMaster(0);
        Climber.getInstance().setWinchRightMaster(0);
    }
}
