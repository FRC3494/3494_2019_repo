package frc.robot.util;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArcConfig {
    private Vector2d directionIntoTarget;
    private boolean isRightOfTarget;
    private double targetSkewAngle;
    private double initialAngleToTargetCenter;
    private double initialAngleToTargetEdge;
    private double initialDistToTargetCenter;
    private double initialDistToTargetEdge;

    public ArcConfig() {
        this.setDirectionIntoTarget();
        this.setTargetSkewAngle();
        this.setInitialAngleToTargetCenter();
        this.setInitialAngleToTargetEdge();
        this.setIsRightOfTarget();
    }

    private void setTargetSkewAngle() {
        this.targetSkewAngle = Robot.getFrontLimelightInstance().getSkewAngle();
    }

    public double getTargetSkewAngle() {
        return this.targetSkewAngle;
    }

    private void setPipelineToCenter() {
        if (this.isRightOfTarget) {
            Robot.getFrontLimelightInstance().setPipeline(RobotMap.LIMELIGHT.PIPELINE.CENTER_RIGHT.num());
        } else {
            Robot.getFrontLimelightInstance().setPipeline(RobotMap.LIMELIGHT.PIPELINE.CENTER_LEFT.num());
        }
    }

    private void setPipelineToEdge() {
        if (this.isRightOfTarget) {
            Robot.getFrontLimelightInstance().setPipeline(RobotMap.LIMELIGHT.PIPELINE.EDGE_RIGHT.num());
        } else {
            Robot.getFrontLimelightInstance().setPipeline(RobotMap.LIMELIGHT.PIPELINE.EDGE_LEFT.num());
        }
    }

    private void setInitialDistToTargetCenter() {
        this.setPipelineToCenter();
        this.initialDistToTargetCenter = Robot.getFrontLimelightInstance().getDistance();
    }

    public double getInitialDistToTargetCenter() {
        return this.initialDistToTargetCenter;
    }

    private void setInitialAngleToTargetCenter() {
        this.setPipelineToCenter();
        this.initialAngleToTargetCenter = Robot.getFrontLimelightInstance().getTargetXAngleRad();
    }

    public double getInitialAngleToTargetCenter() {
        return this.initialAngleToTargetCenter;
    }

    private void setInitialAngleToTargetEdge() {
        this.setPipelineToEdge();
        this.initialAngleToTargetEdge = Robot.getFrontLimelightInstance().getTargetXAngleRad();
    }

    public double getInitialAngleToTargetEdge() {
        return this.initialAngleToTargetEdge;
    }


    private void setDirectionIntoTarget() {
        this.directionIntoTarget = new Vector2d(Math.cos(Robot.getFrontLimelightInstance().getSkewAngle()),
                Math.sin(Robot.getFrontLimelightInstance().getSkewAngle()));
    }

    public Vector2d getDirectionIntoTarget(){
        return this.directionIntoTarget;
    }

    private void setIsRightOfTarget() {
        this.isRightOfTarget = Robot.getFrontLimelightInstance().isRightOfTarget();
    }

    public boolean getIsRightOfTarget(){
        return this.isRightOfTarget;
    }
}
