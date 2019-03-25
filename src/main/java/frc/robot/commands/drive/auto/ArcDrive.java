package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.SynchronousPIDF;


public class ArcDrive extends TimedCommand {
    public static int numFailures = 0;
    public double arcRadius;

    private SynchronousPIDF pidControllerLeft, pidControllerRight, pidControllerPosition;



    private Timer timer;
    private double lastTime = 0;



    public ArcDrive(double arcRadius) {
        super(RobotMap.ARC_DRIVE.TIMEOUT);

        requires(Drivetrain.getInstance());

        this.arcRadius = arcRadius;

        this.timer = new Timer();

        this.pidControllerLeft = new SynchronousPIDF(RobotMap.ARC_DRIVE.kP,
                                                RobotMap.ARC_DRIVE.kI,
                                                RobotMap.ARC_DRIVE.kD,
                                                RobotMap.ARC_DRIVE.kF);

        this.pidControllerRight = new SynchronousPIDF(RobotMap.ARC_DRIVE.kP,
                RobotMap.ARC_DRIVE.kI,
                RobotMap.ARC_DRIVE.kD,
                RobotMap.ARC_DRIVE.kF);

        //this.directionIntoTarget = new Vector2d(0, 0);
    }

    private boolean isRightOfTarget(){
        return Robot.getFrontLimelightInstance().isRightOfTarget();
    }

    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        if(numFailures == 2){
            numFailures = 0;
        }

        this.timer.start();

        double leftToRightSpeedRatio = (this.arcRadius - RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2)
                                            / (this.arcRadius + RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2);
        double leftSetpoint;
        double rightSetpoint;
        if(!this.isRightOfTarget()){
            leftSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED;
            rightSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED * leftToRightSpeedRatio;
        }else{
            leftSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED / leftToRightSpeedRatio;
            rightSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED;
        }
        this.pidControllerLeft.setSetpoint(leftSetpoint);
        this.pidControllerRight.setSetpoint(rightSetpoint);

        this.pidControllerLeft.setOutputRange(-1,1);
        this.pidControllerLeft.setDeadband(leftSetpoint / 10);

        this.pidControllerRight.setOutputRange(-1,1);
        this.pidControllerRight.setDeadband(rightSetpoint / 10);

    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double dt = this.timer.get() - lastTime;
        double left = this.pidControllerLeft.calculate(Drivetrain.getInstance().getLeftAverageVelocity(), dt);
        double right = this.pidControllerRight.calculate(Drivetrain.getInstance().getRightAverageVelocity(), dt);
        Drivetrain.getInstance().tankDrive(left, right);

        this.lastTime = timer.get();

    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return !isPossible && ;//add whether its on target
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
