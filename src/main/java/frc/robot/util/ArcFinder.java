package frc.robot.util;

import edu.wpi.first.wpilibj.drive.Vector2d;
import frc.robot.RobotMap;

public class ArcFinder {
    private Vector2d directionIntoTarget;
    private double arcRadius;
    private double initialAngleToTargetCenter;//not set yet
    private double angleBetweenCenterAndEdge;
    private double initialDistToTargetCenter;//not set yet
    private double initialDistToTargetEdge;
    private double initialAngleToTargetEdge;
    private double kiteLegLength;
    private double cornerToTargetDist;
    private double initialDrivingDistance;
    private double initialDistToCorner;
    private double targetSkewAngle;//not set yet
    private double distToTargetAfterArc;
    private double arcAngle;
    private boolean isRightOfTarget;


    private static ArcFinder INSTANCE;

    public static ArcFinder getInstance() {
        return INSTANCE;
    }

    public double calculate(ArcConfig config) {
        this.directionIntoTarget = config.getDirectionIntoTarget();
        this.targetSkewAngle = config.getTargetSkewAngle();

        this.setInitialDistanceToTargetCenter();
        this.initialAngleToTargetCenter = config.getInitialAngleToTargetCenter();

        this.setInitialDistToCorner();

        this.initialAngleToTargetEdge = config.getInitialAngleToTargetEdge();
        this.isRightOfTarget = config.getIsRightOfTarget();



        this.setAngleBetweenCenterAndEdge();
        this.setInitialDistToTargetEdge();

        this.setInitialDrivingDistance();

        this.setKiteLegLength();
        this.setCornerToTargetDist();
        this.setDistToTargetAfterArc();

        this.setArcRadius();
        this.setArcAngle();

        return this.arcRadius;
    }

    public boolean shouldTurn() {
        double angleToTurn;

        double yDist = this.initialDistToCorner * Math.sin(this.initialAngleToTargetCenter);
        double xDist = this.initialDistToCorner * Math.cos(this.initialAngleToTargetCenter);

        if (isRightOfTarget()) {
            angleToTurn = this.initialAngleToTargetEdge - RobotMap.LIMELIGHT.FOV_DEG / 2;
        } else {
            angleToTurn = -this.initialAngleToTargetEdge + RobotMap.LIMELIGHT.FOV_DEG / 2;
        }

        return Math.sqrt(Math.pow(yDist / Math.tan(this.targetSkewAngle), 2)
                + Math.pow(xDist, 2)) >
                (-yDist / Math.tan(targetSkewAngle) +
                        xDist - this.initialDrivingDistance);
    }

    private void setInitialDrivingDistance() {
        this.initialDrivingDistance = (this.initialDistToTargetEdge * Math.cos(RobotMap.LIMELIGHT.FOV_RAD / 2)
                - this.initialDistToTargetEdge * Math.sin(RobotMap.LIMELIGHT.FOV_RAD / 2)) / Math.tan(RobotMap.LIMELIGHT.FOV_RAD / 2);
    }

    private void setAngleBetweenCenterAndEdge() {
        this.angleBetweenCenterAndEdge = this.initialAngleToTargetEdge - this.initialAngleToTargetCenter;
    }

    private void setArcAngle() {
        this.arcAngle = Math.acos(1
                - (Math.pow(this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.x * this.distToTargetAfterArc - this.initialDrivingDistance, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.y * this.distToTargetAfterArc, 2))
                / (2 * Math.pow(this.arcRadius, 2)));
    }

    //this.directionIntoTarget.rotate() takes degrees and rotates counterclockwise
    private Vector2d getRotatedDirectionIntoTarget() {
        Vector2d rotatedDirection = new Vector2d(this.directionIntoTarget.x, this.directionIntoTarget.y);
        rotatedDirection.rotate(90);
        return rotatedDirection;
    }

    private void setDistToTargetAfterArc() {
        this.distToTargetAfterArc = this.cornerToTargetDist - this.kiteLegLength;
    }

    //equation: sqrt((d_ix - L_x)^2 + (d_iy)^2))
    private void setCornerToTargetDist() {
        this.cornerToTargetDist = Math.sqrt(Math.pow(
                this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter) - this.initialDistToCorner, 2))
                + (Math.pow(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter), 2));
    }

    private void setKiteLegLength() {
        this.kiteLegLength = this.initialDistToCorner - this.initialDrivingDistance;
    }

    private void setInitialDistToCorner() {
        this.initialDistToCorner = -(this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)) / Math.tan(this.targetSkewAngle)
                + (this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter));
    }

    public boolean isPathPossible() {
        return (this.cornerToTargetDist > this.kiteLegLength);
    }


    private double setInitialDistanceToTargetCenter() {
        return 0;
    }

    private void setInitialDistToTargetEdge() {
        this.initialDistToTargetEdge = this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge)
                + Math.sqrt(Math.pow(RobotMap.ARC_DRIVE.CARGO_HATCH_TAPE_WIDTH_FEET, 2) / 4
                - Math.pow(this.initialDistToTargetCenter, 2)
                + Math.pow(this.initialDistToTargetCenter * Math.cos(this.angleBetweenCenterAndEdge), 2));
    }

    private void setArcRadius() {
        Vector2d rotatedDirection = getRotatedDirectionIntoTarget();
        this.arcRadius = rotatedDirection.y / rotatedDirection.x * (this.initialDistToTargetCenter * Math.cos(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.x * this.distToTargetAfterArc + this.initialDrivingDistance)
                + this.initialDistToTargetCenter * Math.sin(this.initialAngleToTargetCenter)
                + this.directionIntoTarget.y * this.distToTargetAfterArc;
    }
}
