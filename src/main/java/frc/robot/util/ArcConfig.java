package frc.robot.util;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class ArcConfig {
    private Vector2d directionIntoTarget;
    private boolean isRightOfTarget;
    private double targetSkewAngle;
    private Double initialAngleToTargetCenter;
    private Double initialAngleToTargetEdge;
    private Double initialDistToTargetCenter;
    private Double initialDistToTargetEdge;
    private double targetToPointAngle;
    private double angleBetweenCenterAndEdge;


    public ArcConfig() {
        this.setIsRightOfTarget();
        this.setDirectionIntoTarget();
        this.setTargetSkewAngle();
        this.setInitialAngleToTargetCenter();
        this.setInitialDistToTargetCenter();
        this.setInitialAngleToTargetEdge();
        this.setAngleBetweenCenterAndEdge();
        this.setInitialDistToTargetEdge();
        this.transformPoint(initialDistToTargetCenter, initialAngleToTargetCenter);
        this.transformPoint(initialDistToTargetEdge, initialAngleToTargetEdge);

        //this has to be reset with the transformed angles to center and edge
        this.setAngleBetweenCenterAndEdge();
    }


    private void transformPoint(Double visualDistance, Double visualAngleRad){
        double trueDist;
        double trueAngle;

        if(isRightOfTarget){
            this.targetToPointAngle = Math.PI / 2 - RobotMap.LIMELIGHT.OFFSET_ANGLE_RAD - visualAngleRad;
        }else{
            this.targetToPointAngle = Math.PI / 2 - RobotMap.LIMELIGHT.OFFSET_ANGLE_RAD + visualAngleRad;
        }
        trueDist = Math.sqrt(Math.pow(visualDistance, 2) + Math.pow(RobotMap.LIMELIGHT.OFFSET_MAGNITUDE, 2) -
                2*visualDistance*RobotMap.LIMELIGHT.OFFSET_MAGNITUDE * Math.cos(targetToPointAngle));
        if(isRightOfTarget){
            trueAngle = Math.asin(Math.sin(targetToPointAngle) / trueDist * visualDistance) -
                        (Math.PI - visualAngleRad - targetToPointAngle);
        }else{
            trueAngle = Math.asin(Math.sin(targetToPointAngle) / trueDist * visualDistance);
        }
        visualDistance = trueDist;
        visualAngleRad = trueAngle;
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

    private void setInitialDistToTargetEdge() {
        this.initialDistToTargetEdge = this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge)
                + Math.sqrt(Math.pow(RobotMap.ARC_DRIVE.CARGO_HATCH_TAPE_WIDTH_FEET, 2) / 4
                - Math.pow(this.initialDistToTargetCenter, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge), 2));
    }

    public double getInitialDistToTargetEdge(){
        return this.initialDistToTargetEdge;
    }

    private void setAngleBetweenCenterAndEdge() {
        this.angleBetweenCenterAndEdge = this.initialAngleToTargetEdge - this.initialAngleToTargetCenter;
    }

    public double getAngleBetweenCenterAndEdge(){
        return this.angleBetweenCenterAndEdge;
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
