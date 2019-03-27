package frc.robot.commands.drive.auto;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.TimedCommand;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.subsystems.Drivetrain;
import frc.robot.util.ArcConfig;
import frc.robot.util.ArcFinder;
import frc.robot.util.SynchronousPIDF;


public class ArcDrive extends TimedCommand {
    private double arcRadius;
    private double arcAngle;
    private double leftToRightRatio;
    private double leftSetpoint;
    private double rightSetpoint;
    private double leftInitialDist;
    private double rightInitialDist;

    private SynchronousPIDF pidControllerLeft, pidControllerRight, pidControllerPositionRatio, pidControllerPosition;

    private ArcConfig config;


    private Timer timer;
    private double lastTime = 0;



    public ArcDrive(double arcRadius) {
        super(RobotMap.ARC_DRIVE.TIMEOUT);

        requires(Drivetrain.getInstance());

        this.arcRadius = arcRadius;

        this.timer = new Timer();

        this.pidControllerLeft = new SynchronousPIDF(RobotMap.ARC_DRIVE.speedkP,
                                                RobotMap.ARC_DRIVE.speedkI,
                                                RobotMap.ARC_DRIVE.speedkD,
                                                RobotMap.ARC_DRIVE.speedkF);

        this.pidControllerRight = new SynchronousPIDF(RobotMap.ARC_DRIVE.speedkP,
                RobotMap.ARC_DRIVE.speedkI,
                RobotMap.ARC_DRIVE.speedkD,
                RobotMap.ARC_DRIVE.speedkF);

        this.pidControllerPositionRatio = new SynchronousPIDF(RobotMap.ARC_DRIVE.ratiokP,
                RobotMap.ARC_DRIVE.ratiokI,
                RobotMap.ARC_DRIVE.ratiokD,
                RobotMap.ARC_DRIVE.ratioKF);

        this.pidControllerPosition = new SynchronousPIDF(1.0, 0,0,0);
        this.pidControllerPosition.setOutputRange(0.1, 1);

        //this.directionIntoTarget = new Vector2d(0, 0);
    }

    private void configureArc(){
        this.config = new ArcConfig();
        ArcFinder.getInstance().calculate(this.config);
        this.arcRadius = ArcFinder.getInstance().getArcRadius();
        this.arcAngle = ArcFinder.getInstance().getArcAngle();
        this.leftToRightRatio = ArcFinder.getInstance().getLeftToRightRatio();


    }

    private void setSetpoints(){
        if(!this.isRightOfTarget()){
            this.leftSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED;
            this.rightSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED * this.leftToRightRatio;
        }else {
            this.leftSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED / this.leftToRightRatio;
            this.rightSetpoint = RobotMap.ARC_DRIVE.MAX_SPEED;
        }



        this.pidControllerLeft.setSetpoint(this.leftSetpoint);
        this.pidControllerRight.setSetpoint(this.rightSetpoint);
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
        this.timer.start();
        this.configureArc();
        this.setSetpoints();

        Drivetrain.getInstance().resetEncoders();

        this.pidControllerLeft.setOutputRange(-1,1);
        this.pidControllerLeft.setDeadband(leftSetpoint / 10);

        this.pidControllerRight.setOutputRange(-1,1);
        this.pidControllerRight.setDeadband(rightSetpoint / 10);

        if(!isRightOfTarget()){
            this.leftInitialDist = (this.arcRadius + RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2) *
                                    this.arcAngle;
            this.rightInitialDist = (this.arcRadius - RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2) *
                    this.arcAngle;
        }else{
            this.leftInitialDist = (this.arcRadius - RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2) *
                    this.arcAngle;
            this.rightInitialDist = (this.arcRadius + RobotMap.ARC_DRIVE.WIDTH_BETWEEN_ROBOT_WHEELS_FEET / 2) *
                    this.arcAngle;
        }

        this.pidControllerPosition.setSetpoint(1);

    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        double dt = this.timer.get() - lastTime;
        double ratio = Drivetrain.getInstance().getLeftAveragePosition() / Drivetrain.getInstance().getRightAveragePosition();
        double percentFinished = (Drivetrain.getInstance().getLeftAveragePosition() / this.leftInitialDist +
                                    Drivetrain.getInstance().getRightAveragePosition() / this.rightInitialDist) / 2;

        double left = this.pidControllerLeft.calculate(Drivetrain.getInstance().getLeftAverageVelocity(), dt);
        double right = this.pidControllerRight.calculate(Drivetrain.getInstance().getRightAverageVelocity(), dt);

        left += this.pidControllerPositionRatio.calculate(ratio, dt);
        right -= this.pidControllerPositionRatio.calculate(ratio, dt);

        left *= this.pidControllerPosition.calculate(percentFinished, dt);
        right *= this.pidControllerPosition.calculate(percentFinished, dt);

        Drivetrain.getInstance().tankDrive(left, right);

        this.lastTime = timer.get();

        this.configureArc();
        this.setSetpoints();

    }

    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return pidControllerPosition.onTarget(.05);//add whether its on target
    }

    @Override
    protected void end() {

    }

    @Override
    protected void interrupted() {
        super.interrupted();
    }
}
