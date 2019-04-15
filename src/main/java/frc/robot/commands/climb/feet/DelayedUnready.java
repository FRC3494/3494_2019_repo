package frc.robot.commands.climb.feet;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.subsystems.Climber;

public class DelayedUnready extends Command {

    private Timer timer;
    private boolean done = false;

    public DelayedUnready() {
        requires(Climber.getInstance());
        this.timer = new Timer();
    }

    @Override
    protected void initialize() {
        this.timer.reset();
        this.timer.start();
    }

    @Override
    protected void execute() {
        if (this.timer.get() >= 0.5) {
            Climber.getInstance().setFrontFoot(DoubleSolenoid.Value.kForward);
            Climber.getInstance().setRearFeet(DoubleSolenoid.Value.kReverse);
            this.done = true;
        } else if (!OI.getInstance().climberSafetyOff()) {
            this.done = true;
        }
    }

    @Override
    protected boolean isFinished() {
        return this.done;
    }

    @Override
    protected void end() {
        this.timer.stop();
    }
}
